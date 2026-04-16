(() => {
  // Set year in footer
  const yearEl = document.getElementById("year");
  if (yearEl) {
    yearEl.textContent = String(new Date().getFullYear());
  }

  // Initialize tabs
  const tabBtns = document.querySelectorAll(".tab-btn");
  const tabContents = document.querySelectorAll(".tab-content");

  tabBtns.forEach((btn) => {
    btn.addEventListener("click", () => {
      const tabId = btn.getAttribute("data-tab");

      // Remove active from all
      tabBtns.forEach((b) => b.classList.remove("active"));
      tabContents.forEach((c) => c.classList.remove("active"));

      // Add active to clicked
      btn.classList.add("active");
      const content = document.getElementById(tabId + "-tab");
      if (content) {
        content.classList.add("active");
      }
    });
  });

  // Setup reveal animations
  const revealTargets = Array.from(
    document.querySelectorAll(".reveal, [data-reveal]")
  );
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
