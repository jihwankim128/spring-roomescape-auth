import {fetchCurrentMember} from "./auth.js";

document.addEventListener("DOMContentLoaded", async () => {
  const loginLink = document.querySelector("[data-nav-login]");
  const signupLink = document.querySelector("[data-nav-signup]");
  const adminLink = document.querySelector("[data-nav-admin]");
  const userLabel = document.querySelector("[data-nav-user]");
  const protectedLinks = document.querySelectorAll('a[href="/reserve"], a[href="/search"]');

  if (!loginLink || !signupLink || !adminLink || !userLabel) {
    return;
  }

  let currentMember = null;

  protectedLinks.forEach((link) => {
    link.addEventListener("click", async (event) => {
      if (currentMember) {
        return;
      }

      event.preventDefault();

      try {
        currentMember = await fetchCurrentMember();
        updateNav(loginLink, signupLink, adminLink, userLabel, currentMember);
        window.location.href = link.href;
      } catch (error) {
        const targetUrl = new URL(link.href);
        const redirectPath = `${targetUrl.pathname}${targetUrl.search}`;
        window.location.href = `/login?redirect=${encodeURIComponent(redirectPath)}`;
      }
    });
  });

  try {
    currentMember = await fetchCurrentMember();
    updateNav(loginLink, signupLink, adminLink, userLabel, currentMember);
  } catch (error) {
    loginLink.hidden = false;
    signupLink.hidden = false;
    adminLink.hidden = true;
    userLabel.hidden = true;
  }
});

function updateNav(loginLink, signupLink, adminLink, userLabel, member) {
  loginLink.hidden = true;
  signupLink.hidden = true;
  adminLink.hidden = member.role !== "ADMIN";
  userLabel.hidden = false;
  userLabel.textContent = member.name;
  userLabel.title = `${member.name}님으로 로그인됨`;
}
