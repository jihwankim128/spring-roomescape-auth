import {requestJson} from "../common/http.js";

export function signUp(name) {
  return requestJson("/api/members", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name })
  });
}
