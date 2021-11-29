package immersionIndustry.contents.blocks;

import arc.*;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ctype.*;
import mindustry.content.*;

import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.experimental.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import immersionIndustry.contents.IMItems;
import immersionIndustry.contents.IMFx;
import immersionIndustry.contents.drawer.*;
import immersionIndustry.types.blocks.production.*;
import immersionIndustry.types.blocks.distribution.*;
import immersionIndustry.types.innerenergy.blocks.*;

//实现ContentList
public class IMBlocks implements ContentList {
  
  public static Block t1ChipFactory,collapseExtractor,collapseQuantumCultivation,auroraGuide,innerEnergyPipe,nearNerenergyConductor;
  
  //在这里创建变量
  @Override
  public void load() {
    
    nearNerenergyConductor = new NearNerenergyConductor("near-nerenergy-nonductor") {{
      health = 200*size*size;
      size = 1;
      requirements(Category.power, ItemStack.with(Items.silicon, 15,IMItems.t1BasicChip, 5,Items.lead,60));
      consumes.power(1);
    }};
    
    innerEnergyPipe = new InnerenergyBlock("inner-energy-pipe"){{
      health = 200*size*size;
      size = 1;
      requirements(Category.power, ItemStack.with(Items.copper, 6,Items.lead,6));
    }};
    
    auroraGuide = new LaserTransmitter("aurora-guide"){{
      health = 200*size*size;
      size = 1;
      requirements(Category.distribution, ItemStack.with(Items.silicon, 35,Items.copper, 75,Items.lead,60,IMItems.t1BasicChip,6,IMItems.cuTiAlloy,6));
      consumes.power(2);
    }};
    
    t1ChipFactory = new GenericCrafter("T1-chip-factory"){{
      health = 200*size*size;
      size = 2;
      updateEffect = IMFx.dispersion;
      outputItem = new ItemStack(IMItems.t1BasicChip, 1);
      requirements(Category.crafting, ItemStack.with(Items.silicon, 45,Items.titanium, 75,Items.lead,120));
      consumes.items(ItemStack.with(Items.silicon, 1, Items.lead, 2));
      consumes.power(5);
      drawer = new DrawLightBlock();
    }};
    
    collapseQuantumCultivation = new GenericCrafter("collapse-quantum-cultivation"){{
      health = 200*size*size;
      size = 4;
      outputItem = new ItemStack(IMItems.collapseQuantum, 1);
      requirements(Category.crafting, ItemStack.with(Items.surgeAlloy, 25,Items.silicon, 35,Items.phaseFabric,22));
      consumes.items(ItemStack.with(Items.plastanium, 1, Items.thorium, 2,Items.phaseFabric,1));
      consumes.power(6);
      drawer = new DrawSwirlBlock();
    }};
    
    collapseExtractor = new IntelligentMiningMachine("collapse-extractor"){{
      requirements(Category.production, ItemStack.with(Items.silicon, 95,IMItems.thTiAlloy, 95,Items.surgeAlloy, 182,IMItems.collapseQuantum,32,IMItems.t1BasicChip,52));
      consumes.power(30);
      health = 200*size*size;
      size = 3;
    }};
  }
  
}