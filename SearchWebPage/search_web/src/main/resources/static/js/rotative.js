const state = new Map([
  ["ALBUM", { page: 0, totalPages: 1 }],
  ["SINGLE", { page: 0, totalPages: 1 }],
]);

const pageSize = 4;
const buttons = document.querySelectorAll(".album-button");
const artistId = document.body.dataset.artistId;

buttons.forEach(btn => {
  btn.addEventListener("click", () => {
    buttons.forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
    const mode = btn.dataset.mode;
    showInfo(mode);
  });
});

async function showInfo(mode) {
  Mustache.tags = ['[[', ']]'];
  activeButtons(mode);
  if (mode === "TRACKS"){
    
  } else {
    const template = document.getElementById("tpl-releases").innerHTML;
    let toDisplay = await fetch(`/api/albums?type=${mode}&artist=${artistId}&page=${state.get(mode).page}&size=${pageSize}`);
    let data = await toDisplay.json();
    state.set(mode, {page: data.number, totalPages: data.totalPages});
    const html = Mustache.render(template, { albums: data.content });
    document.getElementById("album-pager").innerHTML =  html;
  }
  updateArrows(mode);
}

function activeButtons(mode){
  const trackDiv = document.getElementById("track-div");
  const left = document.getElementById("rotate-left");
  const right = document.getElementById("rotate-right");
  const pager = document.getElementById("album-pager");

  const isTracks = mode === "TRACKS";
  if (isTracks){
    document.getElementById("album-pager").innerHTML = "";
  }
  
  trackDiv.classList.toggle("hidden", !isTracks);
  left.classList.toggle("hidden", isTracks);
  right.classList.toggle("hidden", isTracks);
  pager.classList.toggle("hidden", isTracks);
}

function updateArrows(mode) {
  const s = state.get(mode);
  if (!s) return;

  const { page, totalPages } = s;

  const left = document.getElementById("rotate-left");
  const right = document.getElementById("rotate-right");

  const leftDisabled = page === 0;
  const rightDisabled = page >= totalPages - 1;

  left.disabled = leftDisabled;
  right.disabled = rightDisabled;
}

function changePage(delta) {
  const mode = document.querySelector(".album-button.active").dataset.mode.trim().toUpperCase();
  const current = state.get(mode);

  if (!current) return;
  const newPage = Math.min(
    current.totalPages - 1,
    Math.max(0, current.page + delta)
  );
  state.set(mode, { ...current, page: newPage });
  showInfo(mode);
}


document.getElementById("rotate-left").addEventListener("click", () => changePage(-1));

document.getElementById("rotate-right").addEventListener("click", () => changePage(1));

document.addEventListener("DOMContentLoaded", () => {
  buttons.forEach(b => b.classList.remove("active"));
  document.getElementById("mode-albums")?.classList.add("active");
  showInfo("ALBUM");
});
