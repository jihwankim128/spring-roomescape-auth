import {fetchCurrentMember} from "./auth.js";

document.addEventListener("DOMContentLoaded", async () => {
  const loginLink = document.querySelector("[data-nav-login]");
  const signupLink = document.querySelector("[data-nav-signup]");
  const adminLink = document.querySelector("[data-nav-admin]");
  const userLabel = document.querySelector("[data-nav-user]");

  if (!loginLink || !signupLink || !adminLink || !userLabel) {
    return;
  }

  try {
    const member = await fetchCurrentMember();

    loginLink.hidden = true;
    signupLink.hidden = true;
    adminLink.hidden = member.role !== "ADMIN";
    userLabel.hidden = false;
    userLabel.textContent = member.name;
    userLabel.title = `${member.name}님으로 로그인됨`;
  } catch (error) {
    loginLink.hidden = false;
    signupLink.hidden = false;
    adminLink.hidden = true;
    userLabel.hidden = true;
  }
});
