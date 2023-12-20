package pages.commonElements;

import com.healthgraph.SeleniumFramework.TestNG.TestBase;
import com.healthgraph.SeleniumFramework.Util.Common.Cls_Generic_Methods;
import org.openqa.selenium.WebElement;

import java.text.DecimalFormat;


public class InventoryCommonActions extends TestBase {
    /**
     * Returns a formatted STRING value of Final Calculation with two decimal places
     *
     * @param sUnitSellingPriceWithTax Input
     * @param sTaxPercentage           Input
     *
     *                                 <p>
     *                                 tax is inclusive calculated as :
     *                                 (selling_price * tax_rate)/(100 + tax_rate)
     *                                 </p>
     * @return Formatted <b>String</b> value of Final Calculation
     */

    public static String getSellingPriceAmountWithoutTax(String sUnitSellingPriceWithTax, String sTaxPercentage) {

        double dTotalSellingPriceWithoutTax = 0;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        try {
            double dUnitSellingPriceWithTax = Double.parseDouble(sUnitSellingPriceWithTax);

            sTaxPercentage = sTaxPercentage.replaceAll(" ", "").replace("tax", "").replace("%", "");
            double dTaxPercentage = Double.parseDouble(sTaxPercentage);

            double dTaxPercentageAmount;
            dTaxPercentageAmount = (dUnitSellingPriceWithTax * dTaxPercentage) / (100 + dTaxPercentage);
            dTotalSellingPriceWithoutTax = dUnitSellingPriceWithTax - dTaxPercentageAmount;

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

        return decimalFormat.format(dTotalSellingPriceWithoutTax);
    }

    /**
     * Returns a formatted STRING value of Final Calculation with two decimal places
     *
     * @param sOtherCharges Input
     * @param sNetAmount    Input
     * @param sPlusOrMinusAction       Input
     *
     *                      <p>If the action is "plus" -> add the other charges in net amount</p>
     *                      <p>If the action is "minus" -> subtract the other charges from net amount</p>
     * @return Formatted <b>String</b> value of Final Calculation
     */

    public static String getOtherChargesNetAmount(String sOtherCharges, String sNetAmount, String sPlusOrMinusAction) {

        double dOtherChargesNetAmount = 0;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        try {
            double dOtherCharges = Double.parseDouble(sOtherCharges);
            double dNetAmount = Double.parseDouble(sNetAmount);

            if (sPlusOrMinusAction.equalsIgnoreCase("plus") || sPlusOrMinusAction.equalsIgnoreCase("+")) {
                dOtherChargesNetAmount = dNetAmount + dOtherCharges;
            } else if (sPlusOrMinusAction.equalsIgnoreCase("minus") || sPlusOrMinusAction.equalsIgnoreCase("-")) {
                dOtherChargesNetAmount = dNetAmount - dOtherCharges;
            }

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

        return decimalFormat.format(dOtherChargesNetAmount);
    }

    public static String convertTaxPercentageToAmount(String sTaxPercentage) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double dTaxPercentageAmount=0.0;

        try {

            sTaxPercentage = sTaxPercentage.replaceAll(" ", "").replace("tax", "").replace("%", "");
            double dTaxPercentage = Double.parseDouble(sTaxPercentage);


            dTaxPercentageAmount = dTaxPercentage/100;

        } catch (Exception e) {
            m_assert.assertFatal("" + e);
            e.printStackTrace();
        }

        return decimalFormat.format(dTaxPercentageAmount);
    }
}
