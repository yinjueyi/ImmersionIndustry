package immersionIndustry.types.blocks.units;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.scene.ui.layout.Table;
import mindustry.ai.types.*;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;
import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;

import immersionIndustry.IMColors;
import immersionIndustry.contents.IMFx;
import immersionIndustry.ai.TransportAi;

/*
*码头方块
*/
public class DockBlock extends UnitBlock {
  
  public int capacity = 800;
  public TransportShip ship;
  
  public DockBlock(String name) {
    super(name);
    rotate = true;
    update = true;
    group = BlockGroup.transportation;
    hasItems = true;
    itemCapacity = capacity;
    outputsPayload = true;
    configurable = true;
    ship = new TransportShip("transport-ship");
  }
  
  public class DockBlockBuild extends UnitBuild {
    
    public int link = -1;//连接的方块
    public Unit unit;
    
    @Override
    public void updateTile(){
      super.updateTile();
      Building link = world.build(this.link);
      if(linkValid()) {
        this.link = link.pos();
      }
      
      moveOutPayload();
    }
    
    @Override
    public boolean onConfigureTileTapped(Building other) {
      if(this == other){
        if(link == -1) deselect();
        link = -1;
          return false;
      }
      
      if(link == other.pos()){
        link = -1;
        return false;
      }else if(other.block == block && other.team == team){
        link = other.pos();
        if(unit == null) {
          unit = ship.create(team);
          payload = new UnitPayload(unit);
          payVector.setZero();
          Events.fire(new UnitCreateEvent(payload.unit, this));
          unit.controller(new TransportAi(this,other));
        }
        return false;
      }
      
      return true;
    }
    
    @Override
    public void drawConfigure() {
      if(linkValid()){
        float sin = Mathf.absin(Time.time, 6f, 1f);
        Building target = world.build(link);
        Drawf.circles(target.x, target.y, (target.block().size / 2f + 1) * tilesize + sin - 2f, Pal.place);
        Drawf.arrow(x, y, target.x, target.y, size * tilesize + sin, 4f + sin);
      }
    }
    
    @Override
    public void buildConfiguration(Table table) {
      
    };
    
    protected boolean linkValid(){
      if(link == -1) return false;
      if(!(world.build(this.link) instanceof DockBlockBuild)) return false;
      DockBlockBuild other = (DockBlockBuild) world.build(this.link);
      return other.block == block && other.team == team;
    }
  }
  
  public class TransportShip extends UnitType {
    
    public TransportShip(String name) {
      super(name);
      constructor = UnitWaterMove::create;
      itemCapacity = capacity;
      speed = 1.1f;
      drag = 0.13f;
      hitSize = 10f;
      health = 280;
      accel = 0.4f;
      rotateSpeed = 3f;
      trailLength = 14;
    }
    
    //将该单位隐藏
    @Override
    public boolean isHidden(){
      return true;
    }
    
  }
  
}