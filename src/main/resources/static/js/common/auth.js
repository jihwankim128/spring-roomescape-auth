import {requestJson} from "./http.js";

export function fetchCurrentMember() {
  return requestJson("/api/auth/me");
}
