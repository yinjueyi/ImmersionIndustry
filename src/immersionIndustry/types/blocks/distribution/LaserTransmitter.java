package immersionIndustry.types.blocks.distribution;

import arc.*;
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

public class LaserTransmitter extends Block {
  
  int maxLength = 30;
  float speed = 2;//倍数
  float interval = 10;
  
  public LaserTransmitter(String name) {
    super(name);
    update = true;
    solid = true;
    configurable = true;
    hasItems = true;
    hasPower = true;
    rotate = true;
    sync = true;
    envEnabled |= Env.space;
  }
  
  @Override
  public void drawPlace(int x, int y, int rotation, boolean valid) {
    super.drawPlace(x,y,rotation,valid);
    if(!valid) return;
    if(rotation == 0) {
      for(int i = 1;i<maxLength;i++) {
        Tile tile = world.tile(x+i, y);
        if(tile.block() != null && tile.block().hasItems) {
          tile.build.drawConfigure();
          Drawf.dashLine(IMColors.colorYellow,x * tilesize + offset,y * tilesize + offset,tile.drawx(),tile.drawy());
          return;
        }
      }
    }
    if(rotation == 1) {
      for(int i = 1;i<maxLength;i++) {
        Tile tile = world.tile(x, y+i);
        if(tile.block() != null && tile.block().hasItems) {
          tile.build.drawConfigure();
          Drawf.dashLine(IMColors.colorYellow,x * tilesize + offset,y * tilesize + offset,tile.drawx(),tile.drawy());
          return;
        }
      }
    }
    if(rotation == 2) {
      for(int i = 1;i<maxLength;i++) {
        Tile tile = world.tile(x-i, y);
        if(tile.block() != null && tile.block().hasItems) {
          tile.build.drawConfigure();
          Drawf.dashLine(IMColors.colorYellow,x * tilesize + offset,y * tilesize + offset,tile.drawx(),tile.drawy());
          return;
        }
      }
    }
    if(rotation == 3) {
      for(int i = 1;i<maxLength;i++) {
        Tile tile = world.tile(x, y-i);
        if(tile.block() != null && tile.block().hasItems) {
          tile.build.drawConfigure();
          Drawf.dashLine(IMColors.colorYellow,x * tilesize + offset,y * tilesize + offset,tile.drawx(),tile.drawy());
          return;
        }
      }
    }
  }
  
  public class TransmitterBuild extends Building {
    
    Tile target;
    
    @Override
    public void updateTile() {
      if(target == null || !target.build.isValid()) {
        target = itemTo();
      }
      if(target != null && timer(timerDump,interval)) {
        Item item = items.first();
        if(item != null) {
          if(target.build.acceptItem(this,item)) {
            float time = Mathf.dstm(x,y,target.drawx(),target.drawy()) / tilesize * speed;
            IMFx.takeItemEffect(x,y,target.drawx(),target.drawy(),item.color,time);
            Time.run(time,() -> {
              target.build.handleItem(this,item);
              items.remove(item,1);
            });
          }
        }
      }
    }
    
    @Override
    public void draw() {
      super.draw();
      if(target != null) {
        Drawf.laser(team,Core.atlas.find("minelaser"),Core.atlas.find("minelaser-end"),x,y,x,target.drawy());
      }
    }
    
    @Override
    public boolean acceptItem(Building source, Item item){
        return items.get(item) < getMaximumAccepted(item);
    }
    
    public Tile itemTo() {
      if(rotation == 0) {
        for(int i = 1;i<maxLength;i++) {
          Tile tile = world.tile(tileX()+i, tileY());
          if(tile.block() != null && tile.block().hasItems) {
            return tile;
          }
        }
      }
      if(rotation == 1) {
        for(int i = 1;i<maxLength;i++) {
          Tile tile = world.tile(tileX(), tileY()+i);
          if(tile.block() != null && tile.block().hasItems) {
            return tile;
          }
        }
      }
      if(rotation == 2) {
        for(int i = 1;i<maxLength;i++) {
          Tile tile = world.tile(tileX()-i, tileY());
          if(tile.block() != null && tile.block().hasItems) {
            return tile;
          }
        }
      }
      if(rotation == 3) {
        for(int i = 1;i<maxLength;i++) {
          Tile tile = world.tile(tileX(), tileY()-i);
          if(tile.block() != null && tile.block().hasItems) {
            return tile;
          }
        }
      }
      return null;
    }
    
  }
  
}