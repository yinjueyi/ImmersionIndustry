package immersionIndustry.contents;
import mindustry.content.*;
import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.type.*;
import mindustry.graphics.Pal;

import immersionIndustry.IMColors;

public class IMItems implements ContentList {
  
  public static Item t1BasicChip,collapseQuantum;
  
  @Override
  public void load() {
    t1BasicChip = new Item("T1-basic-chip",IMColors.colorPrimary){{
      cost = 2;
    }};
    
    collapseQuantum = new Item("collapse-quantum",Pal.lancerLaser);
  }
  
}
