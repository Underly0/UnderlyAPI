package me.underly0.underlyapi.common.menu.action;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractMenuQuoteAction implements MenuQuoteAction {
    private String quote;
}
