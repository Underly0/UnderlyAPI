package me.underly0.underlyapi.common.menu.action;

import lombok.Getter;
import lombok.Setter;
import me.underly0.underlyapi.api.menu.action.MenuQuoteAction;

@Setter
@Getter
public abstract class AbstractMenuQuoteAction implements MenuQuoteAction {
    private String quote;
}
