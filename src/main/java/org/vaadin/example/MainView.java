package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired GreetService service) {
        VerticalLayout todoslist = new VerticalLayout();
        TextField taskField = new TextField();
        Button addButton = new Button("Add");

        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(click -> {
            Checkbox checkbox = new Checkbox(taskField.getValue());
            todoslist.add(checkbox);
        });

        ProgressBar pgBar = new ProgressBar(0, 100, 50);
        pgBar.addClassName("pg-bar");

        HorizontalLayout layoutButton = new HorizontalLayout();

        Button btPlus = new Button("+");
        Button btMoins = new Button("-");

        layoutButton.add(btMoins, btPlus);

        btMoins.addClickListener(click -> {
            pgBar.setValue(pgBar.getValue() - 10);
            if(pgBar.getValue() <= pgBar.getMin()){
                pgBar.setValue(pgBar.getMax());
            }
        });

        btPlus.addClickListener(click -> {            
            if(pgBar.getValue() >= pgBar.getMax()){
                pgBar.setValue(pgBar.getMin());
            }
            pgBar.setValue(pgBar.getValue() + 10);
        });

        H6 txtDetail = new H6("Todo liste d'exemple");
        txtDetail.addClassName("txtDetail");

        add(
            new H1("Vaadin Todo"),
            new Details("Afficher plus", txtDetail),
            layoutButton,
            pgBar,
            todoslist,
            new HorizontalLayout(taskField, addButton)
        );
    }

}
