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
      table.table(Styles.black,cont -> {
        cont.add(getContName()+":");
        cont.row();
        cont.table(color -> {
          color.image().height(16).width(16).color(IMColors.colorPrimary);
          color.row();
          color.image().height(16).width(16).color(IMColors.colorYellow);
        }).pad(6).left();
        cont.row();
        cont.table(d -> {
          d.table(top -> {
            for(int i = 0; i<size;i++) {
              top.add(new View(IMColors.colorPrimary,false)).size(size * tilesize*2);
            }
          });
          d.row();
          d.table(left -> {
            for(int i = 0; i<size;i++) {
              left.add(new View(IMColors.colorPrimary,false)).size(size * tilesize*2);
            }
          });
          d.table(new TextureRegionDrawable(uiIcon)).size(size * tilesize*2);
          d.table(right -> {
            for(int i = 0; i<size;i++) {
              right.add(new View(IMColors.colorPrimary,false)).size(size * tilesize*2);
            }
          });
          d.row();
          d.table(bottom -> {
            for(int i = 0; i<size;i++) {
              bottom.add(new View(IMColors.colorPrimary,false)).size(size * tilesize*2);
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
      this.export = export;
    }
    
    @Override
    public void draw(){
      super.draw();
      if(export) {
        Fill.square(x,y,width/2);
      }else {
        Fill.circle(x,y,width/2);
      }
    }
    
  }
  
}