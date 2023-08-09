package algonquin.cst2335.group_final_project.Jiebo_Peng_currency;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/** The CurrencyConvert class represents a currency conversion entity, which contains information about the conversion
 * from one currency to another, along with the corresponding amounts.
 * @author Jiebo Peng
 * @version 1.0
 */
@Entity
public class CurrencyConvert {
        /**
         * this is the primary key of the entity.
         * the column name is ID
         */
        @PrimaryKey(autoGenerate = true) //id is primary key
        @ColumnInfo(name="ID")
        public long id;
    /**
     * the FromCurrencyColumn column of the entity
     */
        @ColumnInfo(name="FromCurrencyColumn")
        String fromCurrency;
    /**
     * the ToCurrencyColumn column of the entity
     */
        @ColumnInfo(name="ToCurrencyColumn")
        String toCurrency;
    /**
     * the FromAmountColumn column of the entity
     */
        @ColumnInfo(name="FromAmountColumn")
        String fromAmount;
    /**
     * the ToAmountColumn column of the entity
     */
        @ColumnInfo(name="ToAmountColumn")
        String toAmount;
    /**
     * no parameter constructor
     */
        public CurrencyConvert() {} // for a database query
    /**
     * parameter constructor
     * @param fromCurrency  from currency
     * @param toCurrency  to currency
     * @param fromAmount the mount from currency
     * @param toAmount the mount to currency
     */
        public CurrencyConvert(String fromCurrency, String toCurrency, String fromAmount, String toAmount) {
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.fromAmount = fromAmount;
            this.toAmount = toAmount;
        }
    /**
     * accessor
     * @return get the string from currency
     */
        public String getFromCurrency() {
            return fromCurrency;
        }
    /**
     * accessor
     * @return get the string to currency
     */
        public String getToCurrency() {
            return toCurrency;
        }
    /**
     * accessor
     * @return get the mount from currency
     */
        public String getFromAmount() {
            return fromAmount;
        }
    /**
     * accessor
     * @return get the mount to currency
     */
        public String getToAmount() {
        return toAmount;
    }
}
