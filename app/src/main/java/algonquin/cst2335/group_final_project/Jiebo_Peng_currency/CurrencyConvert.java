package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CurrencyConvert {
        @PrimaryKey(autoGenerate = true) //id is primary key
        @ColumnInfo(name="ID")
        public long id;
        @ColumnInfo(name="FromCurrencyColumn")
        String fromCurrency;
        @ColumnInfo(name="ToCurrencyColumn")
        String toCurrency;
        @ColumnInfo(name="FromAmountColumn")
        String fromAmount;
        @ColumnInfo(name="ToAmountColumn")
        String toAmount;

        public CurrencyConvert() {} // for a database query

        public CurrencyConvert(String fromCurrency, String toCurrency, String fromAmount, String toAmount) {
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.fromAmount = fromAmount;
            this.toAmount = toAmount;
        }

        public String getFromCurrency() {
            return fromCurrency;
        }

        public String getToCurrency() {
            return toCurrency;
        }

        public String getFromAmount() {
            return fromAmount;
        }

        public String getToAmount() {
        return toAmount;
    }
}
