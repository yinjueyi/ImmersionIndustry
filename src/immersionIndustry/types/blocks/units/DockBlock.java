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
import mindustry.world.blocks.units.UnitFactory.*;
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

/*
*码头方块
*/
public class DockBlock extends UnitFactory {
  
  public DockBlock(String name) {
    super(name);
    plans = Seq.with(
      new UnitPlan(UnitTypes.flare, 60f * 15, ItemStack.with(Items.silicon, 15))
    );
  }
  
  public class DockBlockBuild extends UnitFactoryBuild {
    
    public int link = -1;//连接的方块
    
    @Override
    public void updateTile(){
      if(currentPlan < 0 || currentPlan >= plans.size){
        currentPlan = -1;
      }
    
      if(consValid() && currentPlan != -1){
        time += edelta() * speedScl * state.rules.unitBuildSpeed(team);
        progress += edelta() * state.rules.unitBuildSpeed(team);
        speedScl = Mathf.lerpDelta(speedScl, 1f, 0.05f);
      }else{
        speedScl = Mathf.lerpDelta(speedScl, 0f, 0.05f);
      }
    
      moveOutPayload();
    
      if(currentPlan != -1 && payload == null){
        UnitPlan plan = plans.get(currentPlan);
        if(plan.unit.isBanned()){
          currentPlan = -1;
          return;
      }
    
      if(progress >= plan.time && consValid()){
        progress %= 1f;
        payload = new UnitPayload(plan.unit.create(team));
        payVector.setZero();
        consume();
        Events.fire(new UnitCreateEvent(payload.unit, this));
                    }
    
        progress = Mathf.clamp(progress, 0, plan.time);
      }else{
          progress = 0f;
      }
      
      Building link = world.build(this.link);
      boolean hasLink = linkValid();
      if(hasLink) {
        this.link = link.pos();
      }
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
  
}
