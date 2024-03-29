package immersionIndustry.types.innerenergy.blocks;

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
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.power.NuclearReactor.*;
import mindustry.world.blocks.production.GenericCrafter.*;

import static mindustry.Vars.*;
import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

import immersionIndustry.IMColors;
import immersionIndustry.contents.IMFx;

public class InnerenergyBlock extends Block {
  
  public float multiple = 0.01f;
  public float loss = 0.01f;
  public float lossInterval = 180f;
  public float updateEffectChance = 0.04f;
  
  public InnerenergyBlock(String name) {
    super(name);
    update = true;
    solid = true;
    sync = true;
    configurable = true;
  }
  
  @Override
  public void setBars() {
    bars.add(Core.bundle.get("stat.innerenergy"),(InnerenergyBuilding entity) -> new Bar(
				() -> Core.bundle.get("stat.innerenergy"),
				() -> Color.orange,
				() -> entity.inner / 1)
		);
  }
  
  @Override
  public void drawPlace(int x, int y, int rotation, boolean valid) {
    super.drawPlace(x,y,rotation,valid);
    for(int i = 0;i<4;i++) {
      Building build = world.tiles.get(x,y).nearbyBuild(i);
      if(build != null && build.isValid()) {
        if(getBuildingInnerenergy(build) > 0) {
          drawOtherConfigure(build);
        }
      }
    }
  }
  
  public float getBuildingInnerenergy(Building build) {
    if(build != null && build.isValid()) {
      if(build instanceof InnerenergyBuilding entity) {
        return entity.inner;
      }
    }
    return 0;
  }
  
  public void addOtherInnerenergy(Building build,float add) {
    if(build != null && build.isValid()) {
      if(build instanceof InnerenergyBuilding entity) {
        if(entity.acceptInner(build,add)) entity.handleInner(build,add);
      }
    }
  }
  
  public void drawOtherConfigure(Building build) {
    Drawf.select(build.x, build.y,build.block.size * tilesize / 2f + 2f + Mathf.absin(Time.time, 4f, 1f),Pal.place);
  }
  
  public class InnerenergyBuilding extends Building {
    
    float inner = 0;
    
    @Override
    public void updateTile() {
      wastage();
      absorb();
    }
    
    public void wastage() {
      if(timer(timerDump,lossInterval)) {
        if(inner <= 0) return;
        float l = inner*loss;
        if(inner < l) {
          inner = 0;
        }else {
          inner -= l;
        }
      }
    }
    
    public void absorb() {
      for(int i = 0;i<4;i++) {
        Building build = nearby(i);
        if(build != null && build.isValid()) {
          float in = efficiency(build);
          absorbEffect(in > 0);
          inner += in;
          addOtherInnerenergy(build,-in);
        }
      }
    }
    
    public void absorbEffect(boolean bool) {
      if(Mathf.chanceDelta(updateEffectChance)){
        if(bool) {
          IMFx.absorptionHeat.at(this);
        }else {
          IMFx.lossHeat.at(this);
        }
      }
    }
    
    @Override
    public void drawConfigure() {
      for(int i = 0;i<4;i++) {
        Building build = nearby(i);
        if(build != null && build.isValid()) {
          if(getBuildingInnerenergy(build) < inner) {
            drawOtherConfigure(build);
          }else {
            Drawf.select(build.x, build.y,build.block.size * tilesize,Pal.breakInvalid);
          }
        }
      }
    }
    
    /*
    *效率，如果被禁用，返回0。根据两个Building间的内能差距判断效率。
    */
    public float efficiency(Building build) {
      float gap = getBuildingInnerenergy(build) - inner;
      return gap * multiple;
    }
    
    //是否可增加或减少内能，默认true
    public boolean acceptInner(Building source,float amount) {
      return true;
    }
    
    public void handleInner(Building source,float amount) {
      inner += amount;
    }
    
  }
}