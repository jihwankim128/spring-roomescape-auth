import {qs} from "../common/helpers.js";
import {login} from "./api.js";

document.addEventListener("DOMContentLoaded", () => {
  const form = qs('[data-role="login-form"]');
  const nameInput = qs('[name="name"]', form);
  const message = qs('[data-role="login-message"]', form);

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    try {
      await login(nameInput.value.trim());
      window.location.href = "/";
    } catch (error) {
      message.textContent = error.message;
    }
  });
});
