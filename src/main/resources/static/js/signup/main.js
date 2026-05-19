import {qs} from "../common/helpers.js";
import {signUp} from "./api.js";

document.addEventListener("DOMContentLoaded", () => {
  const form = qs('[data-role="signup-form"]');
  const nameInput = qs('[name="name"]', form);
  const message = qs('[data-role="signup-message"]', form);

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    try {
      await signUp(nameInput.value.trim());
      window.location.href = "/login";
    } catch (error) {
      message.textContent = error.message;
    }
  });
});
