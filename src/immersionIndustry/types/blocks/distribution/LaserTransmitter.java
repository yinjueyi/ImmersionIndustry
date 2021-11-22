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
  float speed = 1;//1帧多少瓷砖
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
          Draw.color(Pal.accent);
          Lines.stroke(1f);
          Lines.square(tile.drawx(), tile.drawy(), tile.block().size * tilesize / 2f + 1f);
          Draw.reset();
          Drawf.dashLine(IMColors.colorYellow,x * tilesize + offset,y * tilesize + offset,tile.drawx(),tile.drawy());
          return;
        }
      }
    }
    if(rotation == 1) {
      for(int i = 1;i<maxLength;i++) {
        Tile tile = world.tile(x, y+i);
        if(tile.block() != null && tile.block().hasItems) {
          Draw.color(Pal.accent);
          Lines.stroke(1f);
          Lines.square(tile.drawx(), tile.drawy(), tile.block().size * tilesize / 2f + 1f);
          Draw.reset();
          Drawf.dashLine(IMColors.colorYellow,x * tilesize + offset,y * tilesize + offset,tile.drawx(),tile.drawy());
          return;
        }
      }
    }
    if(rotation == 2) {
      for(int i = 1;i<maxLength;i++) {
        Tile tile = world.tile(x-i, y);
        if(tile.block() != null && tile.block().hasItems) {
          Draw.color(Pal.accent);
          Lines.stroke(1f);
          Lines.square(tile.drawx(), tile.drawy(), tile.block().size * tilesize / 2f + 1f);
          Draw.reset();
          Drawf.dashLine(IMColors.colorYellow,x * tilesize + offset,y * tilesize + offset,tile.drawx(),tile.drawy());
          return;
        }
      }
    }
    if(rotation == 3) {
      for(int i = 1;i<maxLength;i++) {
        Tile tile = world.tile(x, y-i);
        if(tile.block() != null && tile.block().hasItems) {
          Draw.color(Pal.accent);
          Lines.stroke(1f);
          Lines.square(tile.drawx(), tile.drawy(), tile.block().size * tilesize / 2f + 1f);
          Draw.reset();
          Drawf.dashLine(IMColors.colorYellow,x * tilesize + offset,y * tilesize + offset,tile.drawx(),tile.drawy());
          return;
        }
      }
    }
  }
  
  public class TransmitterBuild extends Building {
    
    Building build;
    
    @Override
    public void updateTile() {
      if(build == null || !build.isValid()) {
        Building build = itemTo();
      }
      if(build != null && timer(timerDump,interval)) {
        Item item = items.first();
        if(item != null) {
          if(build.acceptItem(this,item)) {
            float time = Mathf.dstm(x,y,build.x,build.y) / tilesize * speed;
            IMFx.takeItemEffect(x,y,build.x,build.y,item.color,time);
            Time.run(time,() -> {
              build.handleItem(this,item);
              items.remove(item,1);
            });
          }
        }
      }
    }
    
    public void draw() {
      super.draw();
      if(build != null) {
        Drawf.laser(team,Core.atlas.find("minelaser"),Core.atlas.find("minelaser-end"),x,y,build.x,build.y);
      }
    }
    
    public Building itemTo() {
      if(rotation == 0) {
        for(int i = 1;i<maxLength;i++) {
          Tile tile = world.tile(tileX()+i, tileY());
          if(tile.block() != null && tile.block().hasItems) {
            return tile.build;
          }
        }
      }
      if(rotation == 1) {
        for(int i = 1;i<maxLength;i++) {
          Tile tile = world.tile(tileX(), tileY()+i);
          if(tile.block() != null && tile.block().hasItems) {
            return tile.build;
          }
        }
      }
      if(rotation == 2) {
        for(int i = 1;i<maxLength;i++) {
          Tile tile = world.tile(tileX()-i, tileY());
          if(tile.block() != null && tile.block().hasItems) {
            return tile.build;
          }
        }
      }
      if(rotation == 3) {
        for(int i = 1;i<maxLength;i++) {
          Tile tile = world.tile(tileX(), tileY()-i);
          if(tile.block() != null && tile.block().hasItems) {
            return tile.build;
          }
        }
      }
      return null;
    }
    
  }
  
}