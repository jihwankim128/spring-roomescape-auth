import View from "../../common/View.js";
import {emit, formatDateInputValue, on, qs} from "../../common/helpers.js";

export default class ReservationFormView extends View {
  constructor(element) {
    super(element);

    this.themeSelect = qs("#themeSelect", element);
    this.dateInput = qs("#dateInput", element);
    this.submitButton = qs('[data-role="submit-button"]', element);

    this.bindEvents();
  }

  bindEvents() {
    on(this.themeSelect, "change", () => {
      this.emit("@themechange", { themeId: this.themeSelect.value });
    });

    on(this.dateInput, "change", () => {
      this.emit("@datechange", { date: this.dateInput.value });
    });

    on(this.element, "submit", (event) => {
      event.preventDefault();
      emit(this.element, "@submit");
    });
  }

  initializeDate(today = new Date()) {
    const value = formatDateInputValue(today);
    this.dateInput.min = value;
    this.dateInput.value = value;
    return value;
  }

  renderThemes(themes, selectedThemeId) {
    this.themeSelect.innerHTML = '<option value="">테마를 선택하세요</option>';

    themes.forEach((theme) => {
      const option = document.createElement("option");
      option.value = theme.id;
      option.textContent = theme.name;
      this.themeSelect.appendChild(option);
    });

    if (selectedThemeId) {
      this.themeSelect.value = String(selectedThemeId);
    }
  }

  sync({ selectedThemeId, selectedDate, canSubmit, readonly }) {
    this.themeSelect.value = selectedThemeId || "";
    this.dateInput.value = selectedDate || this.dateInput.value;

    this.themeSelect.disabled = readonly;

    this.submitButton.disabled = !canSubmit;
  }
}
