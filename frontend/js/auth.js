document.querySelectorAll("[data-toggle-password]").forEach(function (button) {
  button.addEventListener("click", function () {
    var input = document.getElementById(button.getAttribute("aria-controls"));
    var isHidden = input.type === "password";

    input.type = isHidden ? "text" : "password";
    button.setAttribute("aria-pressed", String(isHidden));
    button.setAttribute("aria-label", isHidden ? "Hide password" : "Show password");
    button.classList.toggle("is-visible", isHidden);
  });
});

document.querySelectorAll("[data-auth-form]").forEach(function (form) {
  var message = form.querySelector(".auth-message");

  function showMessage(text) {
    message.textContent = text;
    message.hidden = false;
  }

  form.addEventListener("submit", function (event) {
    event.preventDefault();
    message.hidden = true;

    if (!form.checkValidity()) {
      showMessage("Please fill in all required fields.");
      form.reportValidity();
      return;
    }

    var password = form.querySelector("#password");
    var confirm = form.querySelector("#confirm-password");
    if (confirm && password.value !== confirm.value) {
      showMessage("Passwords do not match.");
      confirm.focus();
      return;
    }
  });
});