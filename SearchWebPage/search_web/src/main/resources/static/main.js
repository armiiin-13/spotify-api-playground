const buttons = document.querySelectorAll(".entity-button");

buttons.forEach(btn => {
  btn.addEventListener("click", () => {
    buttons.forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
    const tab = btn.dataset.tab;
    showTab(tab);
  });
});

function showTab(tab) {
    document.getElementById("panel-artist").hidden = true;
    document.getElementById("panel-album").hidden = true;
    document.getElementById("panel-track").hidden = true;
    document.getElementById(`panel-${tab}`).hidden = false;
}
