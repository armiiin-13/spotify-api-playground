async function getSpotifyUserToken() {
    const res = await fetch('/spotify/user-token');

    if (!res.ok) {
        throw new Error(`Error on getting the token: ${res.status}`);
    }

    return await res.text();
}

async function transferPlayback(deviceId, token) {
    const res = await fetch('https://api.spotify.com/v1/me/player', {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            device_ids: [deviceId],
            play: false
        })
    });

    if (!res.ok) {
        const text = await res.text();
        throw new Error(`Error in transferPlayback: ${res.status} ${text}`);
    }
}

async function playTrack(deviceId, token, trackUri) {
    const res = await fetch(`https://api.spotify.com/v1/me/player/play?device_id=${deviceId}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            uris: [trackUri]
        })
    });

    if (!res.ok) {
        const text = await res.text();
        throw new Error(`Error in playTrack: ${res.status} ${text}`);
    }
}

function onTrackEnded() {
    const nextButton = document.getElementById("next");

    if (nextButton) {
        nextButton.click();
    } else {
        console.warn("Not found next button");
    }
}

let spotifyPlayer = null;
let spotifyDeviceId = null;
let playButtonBound = false;
let trackEndedHandled = false;
let lastTrackId = null;
let previousPosition = 0;
let previousPaused = true;

const TRACK_URI_BASE = 'spotify:track:';

window.onSpotifyWebPlaybackSDKReady = async () => {
    try {
        const firstToken = await getSpotifyUserToken();

        spotifyPlayer = new Spotify.Player({
            name: 'Web reproductor',
            getOAuthToken: async cb => {
                try {
                    const freshToken = await getSpotifyUserToken();
                    cb(freshToken);
                } catch (e) {
                    console.error('Error obtaining token for SDK:', e);
                }
            },
            volume: 0.5
        });

        spotifyPlayer.addListener('player_state_changed', state => {
            if (!state) return;

            console.log('player_state_changed:', state);

            const currentTrack = state.track_window.current_track;
            const currentTrackId = currentTrack ? currentTrack.id : null;
            const currentPosition = state.position;
            const currentPaused = state.paused;

            if (lastTrackId !== currentTrackId) {
                trackEndedHandled = false;
            }

            const trackReallyEnded =
                !previousPaused &&
                previousPosition > 0 &&
                currentPaused &&
                currentPosition === 0 &&
                !trackEndedHandled;

            if (trackReallyEnded) {
                trackEndedHandled = true;
                onTrackEnded();
            }

            previousPosition = currentPosition;
            previousPaused = currentPaused;
            lastTrackId = currentTrackId;
        });

        spotifyPlayer.addListener('ready', async ({ device_id }) => {
            spotifyDeviceId = device_id;

            const playButton = document.getElementById('play-pause');
            if (!playButton || playButtonBound) return;

            playButtonBound = true;

            playButton.addEventListener('click', async () => {
                try {
                    const token = await getSpotifyUserToken();
                    await spotifyPlayer.activateElement();
                    const state = await spotifyPlayer.getCurrentState();

                    if (state === null) {
                        await transferPlayback(spotifyDeviceId, token);
                        const trackInput = document.getElementById("track-id");
                        const id = trackInput ? trackInput.value : "";

                        if (id !== "") {
                            await playTrack(spotifyDeviceId, token, TRACK_URI_BASE + id);
                        } else {
                            console.warn("No track id found");
                        }
                        return;
                    }

                    await spotifyPlayer.togglePlay();
                } catch (e) {
                    console.error('Error on clicking play/pause:', e);
                }
            });

            setTimeout(() => {
                playButton.click();
            }, 500);
        });

        spotifyPlayer.addListener('not_ready', ({ device_id }) => {
            console.warn('Device not ready:', device_id);
        });

        const connected = await spotifyPlayer.connect();
        console.log('SDK connected:', connected, 'token initial ok:', !!firstToken);

    } catch (e) {
        console.error('Error initializing spotify-player.js:', e);
    }
};