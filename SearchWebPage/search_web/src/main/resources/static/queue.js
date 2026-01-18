document.addEventListener("click", async (e) => {
    const btn = e.target.closest(".add-button");
    if (!btn) return;
    const id = btn.dataset.id;

    try {
        const res = await fetch(`/queue/${id}`, {method: "POST"});
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
    } catch (err) {
        console.error(err);
        alert("Error adding to queue");
    }
  });