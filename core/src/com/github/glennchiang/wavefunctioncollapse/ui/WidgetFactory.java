package com.github.glennchiang.wavefunctioncollapse.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class WidgetFactory {
    private static WidgetFactory instance;
    private final Skin skin = new Skin();

    public static WidgetFactory getInstance() {
        if (instance == null) {
            instance = new WidgetFactory();
        }
        return instance;
    }

    private WidgetFactory() {
        // Set default texture
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("default", new Texture(pixmap));

        // Set default font
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        skin.add("default", font);

        // Set default label style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);

        // Set default button style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = skin.getFont("default");
        buttonStyle.up = skin.newDrawable("default", Color.WHITE);
        buttonStyle.down = skin.newDrawable("default", Color.WHITE);
        skin.add("default", buttonStyle);
    }

    public Label createLabel(String text) {
        return new Label(text, skin);
    }

    public Button createButton(String text, Color color) {
        TextButton button = new TextButton(text, skin);
        button.setColor(color);
        return button;
    }

}
