import { qs } from "../common/helpers.js";
import Controller from "./Controller.js?v=session-auth";
import Store from "./Store.js?v=session-auth";
import ReservationFormView from "./views/ReservationFormView.js?v=session-auth";
import SlotGridView from "./views/SlotGridView.js?v=session-auth";
import ToastView from "./views/ToastView.js?v=session-auth";

document.addEventListener("DOMContentLoaded", () => {
  const store = new Store();
  const views = {
    formView: new ReservationFormView(qs('[data-role="reservation-form"]')),
    slotGridView: new SlotGridView(qs('[data-role="slot-grid"]')),
    toastView: new ToastView(qs('[data-role="toast"]'))
  };

  const controller = new Controller(store, views);
  controller.initialize();
});
