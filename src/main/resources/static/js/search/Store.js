import {cancelReservation, searchReservations} from "./api.js";

export default class Store {
    constructor() {
        this.page = null;
    }

    async search(page = 1) {
        this.page = await searchReservations(page);
    }

    async cancel(id) {
        await cancelReservation(id);
        this.page.content = this.page.content.filter((reservation) => reservation.id !== Number(id));
    }
}
