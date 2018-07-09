package org.inventivetalent.itembuilder.util;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.lang.reflect.Field;

/**
 * Copyright (c) 2017 ChenJi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 *
 * 本来上面应该有两行东西的，但是出了点纰漏
 * 所以乱码了，所以我删了
 * 作者是沉寂，非本人原创
 * @author ChenJi_
 *
 */

public abstract class FileUtil extends YamlConfiguration{
    private DumperOptions options;
    private Representer representer;
    private Yaml yml;
    private File file;
    public FileUtil(Plugin plugin, String fileName){
        this(new File(plugin.getDataFolder(),fileName));
    }
    public FileUtil(File file){
        super();
        this.file = file;
        this.load();
        this.check();
        this.save();
    }
    public abstract void check();

    public void load(){
        try {
            Files.createParentDirs(file);
            if (!file.exists()) file.createNewFile();
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(stream, Charsets.UTF_8);
            this.load(isr);
            this.getYmal();
            this.getYmalOptions();
            this.getYmalRepresenter();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void loadFromString(String s) throws InvalidConfigurationException {
        super.loadFromString(s);
        this.getYmal();
        this.getYmalOptions();
        this.getYmalRepresenter();
    }
    @Override
    public String saveToString() {
        this.options.setIndent(options().indent());
        this.options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.options.setAllowUnicode(true);
        this.representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        String header = buildHeader();
        String dump = this.yml.dump(getValues(false));
        if (dump.equals("{}\n")) {
            dump = "";
        }
        return header + dump;
    }

    @Override
    public void save(File file) throws IOException {
        if (file == null) {
            return;
        }
        Files.createParentDirs(file);
        String data = this.saveToString();
        FileOutputStream fileout = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fileout, Charsets.UTF_8);
        try {
            writer.write(data.toCharArray());
        } finally {
            writer.close();
        }
    }
    public void save(){
        try {
            save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getYmalOptions() {
        Field f;
        try {
            f = this.getField("yamlOptions");
            f.setAccessible(true);
            this.options = (DumperOptions) f.get(this);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getYmalRepresenter() {
        Field f;
        try {
            f = this.getField("yamlRepresenter");
            f.setAccessible(true);
            this.representer = (Representer) f.get(this);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getYmal() {
        Field f;
        try {
            f = this.getField("yaml");
            f.setAccessible(true);
            this.yml = (Yaml) f.get(this);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private Field getField(String n) {
        for (Field f : YamlConfiguration.class.getDeclaredFields()) {
            if (f.getName().equals(n)) {
                return f;
            }
        }
        return null;
    }
}
