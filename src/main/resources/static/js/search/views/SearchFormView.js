import View from "../../common/View.js";
import {on} from "../../common/helpers.js";

export default class SearchFormView extends View {
    constructor(element) {
        super(element);
        this.bindEvents();
    }

    bindEvents() {
        on(this.element, "submit", (event) => {
            event.preventDefault();
            this.emit("@search");
        });
    }
}
