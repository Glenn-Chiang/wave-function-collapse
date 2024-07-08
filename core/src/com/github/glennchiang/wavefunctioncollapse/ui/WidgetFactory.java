package com.github.glennchiang.wavefunctioncollapse.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

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
        buttonStyle.up = defaultDrawable(Color.WHITE);
        buttonStyle.down = defaultDrawable(Color.WHITE);
        skin.add("default", buttonStyle);

        // Set default select box style
        SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle();
        selectBoxStyle.font = skin.getFont("default");
        List.ListStyle listStyle = new List.ListStyle();
        listStyle.background = defaultDrawable(Color.BLACK);
        listStyle.selection = defaultDrawable(Color.BLACK);
        listStyle.selection.setTopHeight(8);
        listStyle.selection.setBottomHeight(8);
        listStyle.selection.setLeftWidth(8);
        listStyle.selection.setRightWidth(8);
        listStyle.fontColorSelected = Color.valueOf("#03A9F4");
        listStyle.font = skin.getFont("default");
        selectBoxStyle.listStyle = listStyle;
        selectBoxStyle.background = defaultDrawable(Color.valueOf("#29B6F6"));
        selectBoxStyle.backgroundDisabled = defaultDrawable(Color.SLATE);
        selectBoxStyle.background.setTopHeight(8);
        selectBoxStyle.background.setBottomHeight(8);
        selectBoxStyle.background.setLeftWidth(8);
        selectBoxStyle.background.setRightWidth(8);
        selectBoxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
        skin.add("default", selectBoxStyle);
    }

    public Label createLabel(String text) {
        return new Label(text, skin);
    }

    public Button createButton(String text, Color color) {
        TextButton button = new TextButton(text, skin);
        button.setColor(color);
        return button;
    }

    public <T> SelectBox<T> createSelectBox(Array<T> items) {
        SelectBox<T> selectBox = new SelectBox<T>(skin);
        selectBox.setAlignment(Align.center);
        selectBox.setItems(items);
        return selectBox;
    }

    private Drawable defaultDrawable(Color color) {
        return skin.newDrawable("default", color);
    }
}
