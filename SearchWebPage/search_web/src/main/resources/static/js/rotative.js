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
  const template = document.getElementById("tpl-releases").innerHTML;
  let toDisplay = await fetch(`/api/albums?type=${mode}&artist=${artistId}&page=${state.get(mode).page}&size=${pageSize}`);
  let data = await toDisplay.json();
  state.set(mode, {page: data.number, totalPages: data.totalPages});
  const html = Mustache.render(template, { albums: data.content });
  document.getElementById("album-pager").innerHTML =  html;
  updateArrows(mode);
}

function updateArrows(mode) {
  const { page, totalPages } = state.get(mode);

  const left = document.getElementById("rotate-left");
  const right = document.getElementById("rotate-right");

  left.disabled = page === 0;
  right.disabled = page >= totalPages - 1;

  left.classList.toggle("disabled", page === 0);
  right.classList.toggle("disabled", page >= totalPages - 1);
}

document.getElementById("rotate-left").addEventListener("click", () => {
  const mode = document.querySelector(".album-button.active").dataset.mode;
  state.set(mode, Math.max(0, state.get(mode) - 1));
  showInfo(mode);
});

document.getElementById("rotate-right").addEventListener("click", () => {
  const mode = document.querySelector(".album-button.active").dataset.mode;
  state.set(mode, Math.max(state.get(mode).totalPages, state.get(mode) + 1));
  showInfo(mode);
});
