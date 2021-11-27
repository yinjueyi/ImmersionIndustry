package immersionIndustry.types.blocks.distribution;

import arc.*;
import arc.scene.*;
import arc.scene.style.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.scene.ui.layout.Table;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;
import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

import immersionIndustry.IMColors;
import immersionIndustry.contents.IMFx;

public class LogicalDistribution extends Block {
  
  public LogicalDistribution(String name) {
    super(name);
    update = true;
    solid = true;
    configurable = true;
    hasItems = true;
    hasPower = true;
    sync = true;
  }
  
  @Override
  public void init() {
    super.init();
  }
  
  public String getContName() {
    return localizedName;
  }
  
  public class LogicalBuilding extends Building {
    
    @Override
    public void buildConfiguration(Table table) {
      setup(table);
    }
    
    public void setup(Table table) {
      table.clear();
      float width = size * tilesize*4;
      table.table(Styles.black,cont -> {
        cont.add(getContName()+":");
        cont.row();
        cont.table(color -> {
          color.image().height(16).width(16).color(IMColors.colorPrimary);
          color.add(new ItemDisplay(Items.copper));
          color.add(new ItemDisplay(Items.lead));
          color.row();
          color.image().height(16).width(16).color(IMColors.colorYellow);
          color.add(new ItemDisplay(Items.titanium));
          color.add(new ItemDisplay(Items.silicon));
        }).pad(6).left();
        cont.row();
        cont.table(d -> {
          d.table(top -> {
            for(int i = 0; i<size;i++) {
              top.add(new View(IMColors.colorPrimary,false)).size(width);
            }
          });
          d.row();
          d.table(left -> {
            for(int i = 0; i<size;i++) {
              left.add(new View(IMColors.colorPrimary,false)).size(width);
              left.row();
            }
          });
          d.table(new TextureRegionDrawable(uiIcon)).size(width);
          d.table(right -> {
            for(int i = 0; i<size;i++) {
              right.add(new View(IMColors.colorPrimary,false)).size(width);
              right.row();
            }
            right.table(ext -> {
              ext.center();
              ext.add("远程传输");
              ext.add(new View(IMColors.colorPrimary,false)).size(width);
            }).marginLeft(width*2);
          });
          d.row();
          d.table(bottom -> {
            for(int i = 0; i<size;i++) {
              bottom.add(new View(IMColors.colorPrimary,false)).size(width);
            }
          });
        }).pad(6).center();
      }).pad(6);
    }
    
    @Override
    public boolean acceptItem(Building source, Item item) {
      return false;
    }
    
  }
  
  public class View extends BorderImage {
    
    boolean export;
    
    public View(Color color,boolean export) {
      super();
      borderColor = color;
      color = export ? IMColors.colorPrimary : IMColors.colorYellow;
      this.export = export;
    }
    
  }
  
}