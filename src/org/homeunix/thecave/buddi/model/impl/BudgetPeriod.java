package org.homeunix.thecave.buddi.model.impl;

import org.homeunix.thecave.buddi.model.BudgetCategoryType;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BudgetPeriod {
    private final Date startDate;
    private final BudgetCategoryType type;
    private final Date endDate;

    public BudgetPeriod(BudgetCategoryType type, Date date) {
        this.type = type;
        this.startDate = type.getStartOfBudgetPeriod(date);
        this.endDate = type.getEndOfBudgetPeriod(date);
    }

    private BudgetPeriod nextBudgetPeriod() {
        return new BudgetPeriod(type, type.getStartOfNextBudgetPeriod(startDate));
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public long getDayCount() {
        return type.getDaysInPeriod(startDate);
    }

    public Period getPeriod() {
        return new Period(startDate, endDate);
    }

    /**
     * Returns a list of BudgetPeriods, covering the entire range of periods
     * occupied by startDate to endDate.
     *
     * @param end
     * @return
     */
    public List<BudgetPeriod> budgetPeriodsTill(BudgetPeriod end) {
        List<BudgetPeriod> budgetPeriods = new LinkedList<>();

        BudgetPeriod current = this;

        while (current.getStartDate().before(end.getEndDate())) {
            budgetPeriods.add(current);
            current = current.nextBudgetPeriod();
        }

        return budgetPeriods;
    }
}
