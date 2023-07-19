package algonquin.cst2335.group_final_project;
public class OneCurrencyConvert {
        String fromCurrency;
        String toCurrency;
        float amount;

        public OneCurrencyConvert(String fromCurrency, String toCurrency, float amount) {
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.amount = amount;
        }

        public String getFromCurrency() {
            return fromCurrency;
        }

        public String getToCurrency() {
            return toCurrency;
        }

        public float getAmount() {
            return amount;
        }
}
