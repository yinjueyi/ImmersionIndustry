package immersionIndustry.contents.drawer;

import mindustry.world.draw.*;
import arc.graphics.g2d.*;
import arc.Core;
import arc.math.*;
import arc.util.*;
import arc.graphics.Blending;
import mindustry.graphics.Layer;
import mindustry.graphics.Drawf;
import mindustry.graphics.Shaders;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import static mindustry.Vars.*;

import immersionIndustry.IMColors;
import immersionIndustry.contents.IMFx;

public class DrawLightBlock extends DrawBlock {
  
  public TextureRegion light;
  
  @Override
  public void draw(GenericCrafterBuild build){
    Draw.rect(build.block.region, build.x, build.y, build.block.rotate ? build.rotdeg() : 0);
    
    Draw.color(IMColors.colorPrimary,IMColors.colorDarkPrimary,build.warmup);
    Draw.z(Layer.effect);
    
    if(build.efficiency() > 0) {
      GenericCrafter block = (GenericCrafter) build.block;
      if(Mathf.chanceDelta(block.updateEffectChance)){
        IMFx.absorbedEnergy.at(build.x + Mathf.range(block.size * tilesize), build.y + Mathf.range(block.size * tilesize),build.rotation,build);
      }
    }
    for(int i = 0; i < 5 ; i++){
      float rot = build.rotation + i * 360f/5 - Time.time * 0.5f;
      Lines.swirl(build.x, build.y, build.block.size * 4 + 3f, 0.14f, rot);
      Lines.swirl(build.x, build.y, build.block.size * 4 + 8f + Mathf.range(build.block.size * tilesize), 0.14f, rot);
    }
    Drawf.light(build.x, build.y,build.block.size * tilesize * 1.5f, IMColors.colorPrimary, build.warmup);
    Draw.reset();
  }
  
  @Override
  public void load(Block block) {
    light = Core.atlas.find(block.name + "-light");
  }
  
}