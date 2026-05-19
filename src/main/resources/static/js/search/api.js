import {requestJson} from "../common/http.js";

export function searchReservations(page = 1, size = 20) {
    return requestJson(
        `/api/reservations?page=${page}&size=${size}`
    );
}

export function cancelReservation(id) {
    return requestJson(`/api/reservations/${id}`, {
        method: "DELETE"
    });
}
