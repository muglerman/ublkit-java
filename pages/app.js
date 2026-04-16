(() => {
  const yearEl = document.getElementById("year");
  if (yearEl) {
    yearEl.textContent = String(new Date().getFullYear());
  }

  const revealTargets = Array.from(document.querySelectorAll(".reveal, [data-reveal]"));
  if (revealTargets.length === 0) {
    return;
  }

  revealTargets.forEach((el, index) => {
    const stagger = Math.min(index * 55, 660);
    el.style.setProperty("--delay", `${stagger}ms`);
  });

  if (!("IntersectionObserver" in window)) {
    revealTargets.forEach((el) => el.classList.add("is-visible"));
    return;
  }

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (!entry.isIntersecting) {
          return;
        }
        entry.target.classList.add("is-visible");
        observer.unobserve(entry.target);
      });
    },
    {
      threshold: 0.18,
      rootMargin: "0px 0px -8% 0px"
    }
  );

  revealTargets.forEach((el) => observer.observe(el));
})();
