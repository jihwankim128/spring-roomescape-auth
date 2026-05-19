import {qs} from "../common/helpers.js";

import Store from "./Store.js?v=session-auth";
import Controller from "./Controller.js?v=session-auth";

import SearchFormView from "./views/SearchFormView.js?v=session-auth";
import SearchResultView from "./views/SearchResultView.js?v=session-auth";
import ToastView from "./views/ToastView.js?v=session-auth";

document.addEventListener("DOMContentLoaded", () => {
    const store = new Store();

    const views = {
        formView: new SearchFormView(qs('[data-role="search-form"]')),
        resultView: new SearchResultView(qs('[data-role="search-results"]')),
        toastView: new ToastView(qs('[data-role="toast"]'))
    };

    new Controller(store, views).initialize();
});
