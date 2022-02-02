package camunda.poc.services;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

/**
 * Created by ai.khafizov
 * on 01.02.2022
 */
@Named
public class ReserveSeatOnBoat implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Reserve on seat boat");
        String money = "0.0";
        String ticketType = "Coach";

        money = (String) delegateExecution.getVariable("money");
        double moneyDouble = Double.parseDouble(money);

        if(moneyDouble >= 10000) {
            ticketType = "First Class";
        } else if(moneyDouble >= 5000) {
            ticketType = "Business Class";
        } else if(moneyDouble <= 10) {
            ticketType = "Stowaway Class";
            throw new BpmnError("Fall_Overboard", "A Cheap ticket has led to a small wave throwing yu overboard.");
        }

        delegateExecution.setVariable("ticketType", ticketType);
    }
}
