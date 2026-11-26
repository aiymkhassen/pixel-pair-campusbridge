
(function () {
  var page = document.body;
  var toggles = document.querySelectorAll("[data-sidebar-toggle]");
  var backdrop = document.querySelector("[data-sidebar-backdrop]");
  var sidebar = document.getElementById("dashboard-sidebar");
  var mobileQuery = window.matchMedia("(max-width: 820px)");

  function isExpanded() {
    if (mobileQuery.matches) {
      return page.classList.contains("sidebar-open");
    }
    return !page.classList.contains("sidebar-collapsed");
  }

  function updateButtons() {
    var expanded = isExpanded();
    toggles.forEach(function (toggle) {
      toggle.setAttribute("aria-expanded", String(expanded));
      toggle.setAttribute("aria-label", expanded ? "Collapse sidebar" : "Expand sidebar");
    });
  }

  function closeDrawer() {
    page.classList.remove("sidebar-open");
    updateButtons();
  }

  toggles.forEach(function (toggle) {
    toggle.addEventListener("click", function () {
      if (mobileQuery.matches) {
        page.classList.toggle("sidebar-open");
      } else {
        page.classList.toggle("sidebar-collapsed");
      }
      updateButtons();
    });
  });

  backdrop.addEventListener("click", closeDrawer);

  sidebar.addEventListener("click", function (event) {
    if (mobileQuery.matches && event.target.closest(".dashboard-sidebar__link")) {
      closeDrawer();
    }
  });

  document.addEventListener("keydown", function (event) {
    if (event.key === "Escape" && mobileQuery.matches) {
      closeDrawer();
    }
  });

  mobileQuery.addEventListener("change", closeDrawer);

  updateButtons();
})();

(function () {
  var button = document.querySelector("[data-notification-toggle]");
  var panel = document.querySelector("[data-notification-panel]");
  var badge = document.querySelector("[data-notification-badge]");

  function setOpen(open) {
    panel.hidden = !open;
    button.setAttribute("aria-expanded", String(open));
    if (open) {
      badge.classList.add("is-hidden");
    }
  }

  button.addEventListener("click", function () {
    setOpen(panel.hidden);
  });

  document.addEventListener("click", function (event) {
    if (!panel.hidden && !event.target.closest(".dashboard-notifications")) {
      setOpen(false);
    }
  });

  document.addEventListener("keydown", function (event) {
    if (event.key === "Escape" && !panel.hidden) {
      setOpen(false);
      button.focus();
    }
  });
})();

//carousel
(function () {
  var carousel = document.querySelector("[data-carousel]");
  var slides = Array.prototype.slice.call(carousel.querySelectorAll("[data-carousel-slide]"));
  var dotsBox = carousel.querySelector("[data-carousel-dots]");
  var prevButton = carousel.querySelector("[data-carousel-prev]");
  var nextButton = carousel.querySelector("[data-carousel-next]");

  var AUTOPLAY_DELAY = 5000;   // milliseconds between automatic changes
  var current = 0;
  var timer = null;
  var dots = [];

  slides.forEach(function (slide) {
    var img = slide.querySelector("img");
    img.addEventListener("error", function () {
      slide.classList.add("is-missing");
    });
  });

  slides.forEach(function (slide, index) {
    var dot = document.createElement("button");
    dot.type = "button";
    dot.className = "event-carousel__dot";
    dot.setAttribute("aria-label", "Show photo " + (index + 1));
    dot.addEventListener("click", function () {
      showSlide(index);
      restartAutoplay();
    });
    dotsBox.appendChild(dot);
    dots.push(dot);
  });

  function showSlide(index) {
    current = (index + slides.length) % slides.length;

    slides.forEach(function (slide, i) {
      var isActive = i === current;
      slide.classList.toggle("is-active", isActive);
      slide.setAttribute("aria-hidden", String(!isActive));
    });

    dots.forEach(function (dot, i) {
      var isActive = i === current;
      dot.classList.toggle("is-active", isActive);
      if (isActive) {
        dot.setAttribute("aria-current", "true");
      } else {
        dot.removeAttribute("aria-current");
      }
    });
  }

  function stopAutoplay() {
    clearInterval(timer);
    timer = null;
  }

  function startAutoplay() {
    stopAutoplay();
    if (slides.length > 1) {
      timer = setInterval(function () {
        showSlide(current + 1);
      }, AUTOPLAY_DELAY);
    }
  }

  function restartAutoplay() {
    if (carousel.matches(":hover") || carousel.contains(document.activeElement)) {
      stopAutoplay();
    } else {
      startAutoplay();
    }
  }

  prevButton.addEventListener("click", function () {
    showSlide(current - 1);
    restartAutoplay();
  });

  nextButton.addEventListener("click", function () {
    showSlide(current + 1);
    restartAutoplay();
  });

  carousel.addEventListener("mouseenter", stopAutoplay);
  carousel.addEventListener("mouseleave", startAutoplay);
  carousel.addEventListener("focusin", stopAutoplay);
  carousel.addEventListener("focusout", startAutoplay);
  carousel.addEventListener("touchstart", stopAutoplay, { passive: true });
  carousel.addEventListener("touchend", startAutoplay);

  document.addEventListener("visibilitychange", function () {
    if (document.hidden) {
      stopAutoplay();
    } else {
      startAutoplay();
    }
  });

  showSlide(0);
  startAutoplay();
})();