package menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private String className = null;
    private String functionName = null;
    private OptionFunctionalInterface optionFunctionalInterface = null;
}
