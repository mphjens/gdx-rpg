<?xml version="1.0" encoding="UTF-8"?>
<tileset name="roguelike" tilewidth="16" tileheight="16" spacing="1" tilecount="1767" columns="57">
 <image source="../../../../2D Assets/TiledMaps/RogueLike maps/RogueLikeTileSheet.png" width="968" height="526"/>
 <terraintypes>
  <terrain name="Grass" tile="62"/>
  <terrain name="Water" tile="0"/>
 </terraintypes>
 <tile id="0" terrain="1,1,1,1">
  <objectgroup draworder="index"/>
  <animation>
   <frame tileid="0" duration="500"/>
   <frame tileid="1" duration="500"/>
  </animation>
 </tile>
 <tile id="1" terrain="1,1,1,1">
  <objectgroup draworder="index"/>
 </tile>
 <tile id="2" terrain="0,0,0,1"/>
 <tile id="3" terrain="0,0,1,1"/>
 <tile id="4" terrain="0,0,1,0"/>
 <tile id="5" terrain="0,0,0,0"/>
 <tile id="13">
  <objectgroup draworder="index">
   <properties>
    <property name="light" type="bool" value="true"/>
   </properties>
  </objectgroup>
  <animation>
   <frame tileid="13" duration="300"/>
   <frame tileid="14" duration="250"/>
   <frame tileid="13" duration="300"/>
   <frame tileid="14" duration="200"/>
   <frame tileid="13" duration="150"/>
   <frame tileid="14" duration="220"/>
   <frame tileid="13" duration="100"/>
   <frame tileid="14" duration="100"/>
  </animation>
 </tile>
 <tile id="19">
  <objectgroup draworder="index">
   <object id="1" x="7.9375" y="16">
    <polyline points="0,0 0.125,-12.875"/>
   </object>
   <object id="2" x="1.125" y="8.4375">
    <polyline points="0,0 13.6875,-0.0625"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="57" terrain="1,1,1,0"/>
 <tile id="58" terrain="1,1,0,1"/>
 <tile id="59" terrain="0,1,0,1"/>
 <tile id="60" terrain="1,1,1,1"/>
 <tile id="61" terrain="1,0,1,0"/>
 <tile id="62" terrain="0,0,0,0"/>
 <tile id="114" terrain="1,0,1,1"/>
 <tile id="115" terrain="0,1,1,1"/>
 <tile id="116" terrain="0,1,0,0"/>
 <tile id="117" terrain="1,1,0,0"/>
 <tile id="118" terrain="1,0,0,0"/>
 <tile id="181">
  <objectgroup draworder="index">
   <object id="1" x="1.375" y="16">
    <polyline points="0,0 0.0625,-15.9375"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="182">
  <objectgroup draworder="index">
   <object id="1" x="14.4375" y="15.875">
    <polyline points="0,0 0.0625,-15.8125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="470">
  <objectgroup draworder="index">
   <properties>
    <property name="light" type="bool" value="true"/>
    <property name="light.color" value=""/>
    <property name="light.isfire" type="bool" value="true"/>
    <property name="light.range" type="float" value="6"/>
    <property name="light.xray" type="bool" value="false"/>
   </properties>
   <object id="1" x="0.956522" y="12.6522">
    <polyline points="0,0 2,-6.65217 6.34783,-7.73913 10.9565,-6.69565 12.9565,0.26087 9.52174,3.26087 3.47826,3.34783 0,0"/>
   </object>
  </objectgroup>
  <animation>
   <frame tileid="470" duration="400"/>
   <frame tileid="471" duration="200"/>
   <frame tileid="470" duration="200"/>
   <frame tileid="471" duration="400"/>
   <frame tileid="470" duration="200"/>
   <frame tileid="471" duration="200"/>
   <frame tileid="470" duration="200"/>
   <frame tileid="471" duration="200"/>
  </animation>
 </tile>
 <tile id="471">
  <objectgroup draworder="index">
   <object id="3" x="1.08696" y="12.8261">
    <polyline points="0,0 2,-6.65217 6.34783,-7.73913 10.9565,-6.69565 12.9565,0.26087 9.52174,3.26087 3.47826,3.34783 0,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="473">
  <objectgroup draworder="index">
   <properties>
    <property name="light" type="bool" value="true"/>
   </properties>
  </objectgroup>
  <animation>
   <frame tileid="473" duration="150"/>
   <frame tileid="474" duration="140"/>
   <frame tileid="473" duration="100"/>
   <frame tileid="474" duration="150"/>
   <frame tileid="473" duration="100"/>
   <frame tileid="474" duration="110"/>
   <frame tileid="473" duration="150"/>
   <frame tileid="474" duration="100"/>
  </animation>
 </tile>
 <tile id="529">
  <objectgroup draworder="index">
   <object id="1" x="0.0434783" y="13.8696">
    <polyline points="0,0 3.86957,2.08696 10.8696,2.13043 14.913,0.0434783 7.47826,-12.913 0,-0.0869565"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="530">
  <objectgroup draworder="index">
   <object id="1" x="0.015625" y="14">
    <polyline points="0,0 3.86957,2.08696 10.8696,2.13043 14.913,0.0434783 7.47826,-12.913 0,-0.0869565"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="531">
  <objectgroup draworder="index">
   <object id="2" x="0" y="13.9688">
    <polyline points="0,0 3.86957,2.08696 10.8696,2.13043 14.913,0.0434783 7.47826,-12.913 0,-0.0869565"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="536">
  <objectgroup draworder="index">
   <object id="1" x="5" y="16">
    <polyline points="0,0 -5.03804,-5.0625 -5.03125,-10.9497 -0.93071,-15.106 6.8696,-15.125 10.9756,-11.0557 10.8941,-5.09375 5.96875,-0.03125 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="537">
  <objectgroup draworder="index">
   <object id="1" x="0.0078125" y="14.0039">
    <polyline points="0,0 -0.0261285,-5.99609 3.97387,-9.99609 11.9294,-9.99609 15.9091,-6.03957 15.9961,-0.126529 12.8657,2.04738 2.99609,2.04738 0.039572,0.0473845"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="539">
  <objectgroup draworder="index">
   <object id="1" x="5.04348" y="16">
    <polyline points="0.043478,0.0434783 -4.34782,-11.6087 0.434782,-14.087 9.86957,-11.0435 9.39135,-5.43478 6.86956,-0.0869567 0.347827,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="540">
  <objectgroup draworder="index">
   <object id="3" x="4.04348" y="15.913">
    <polyline points="0.869565,0 -4.17391,-11.5217 -0.913044,-15.0435 8.91305,-15 11.087,-8.52174 5.86957,0.130435 0.782609,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="563">
  <objectgroup draworder="index">
   <object id="3" x="2.96875" y="7.3125">
    <polyline points="0,0 4.90625,-1.90625 10.9688,-0.21875 10.0625,6.6875 5.78125,7.78125 1.03125,6.71875 0,0.09375"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="566">
  <objectgroup draworder="index">
   <object id="1" x="7.0625" y="16.0938">
    <polyline points="0,0 -3.25,-6.6875 1.46875,-11.2813 5.9375,-6.59375 2.03125,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="583">
  <objectgroup draworder="index">
   <object id="1" x="7.13043" y="1.04348">
    <polyline points="-1.30435,0 -6.30435,7.01228 -7.20516,14.8505 8.83832,14.8139 7.75136,6.93207 2.67663,0.105978 -1.22962,0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="584">
  <objectgroup draworder="index">
   <object id="4" x="7.30435" y="1.04348">
    <polyline points="-1.30435,0 -6.30435,7.01228 -7.20516,14.8505 8.83832,14.8139 7.75136,6.93207 2.67663,0.105978 -1.22962,0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="585">
  <objectgroup draworder="index">
   <object id="1" x="7.26087" y="1.08696">
    <polyline points="-1.30435,0 -6.30435,7.01228 -7.20516,14.8505 8.83832,14.8139 7.75136,6.93207 2.67663,0.105978 -1.22962,0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="586">
  <objectgroup draworder="index">
   <object id="1" x="7.04348" y="1.13043">
    <polyline points="0,0 -4,10.9688 -4.03125,14.9375 5.96875,15.0313 5.96875,11.0625 1.9375,0.0625 0.03125,0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="587">
  <objectgroup draworder="index">
   <object id="2" x="7.08696" y="1.08696">
    <polyline points="0,0 -4,10.9688 -4.03125,14.9375 5.96875,15.0313 5.96875,11.0625 1.9375,0.0625 0.03125,0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="588">
  <objectgroup draworder="index">
   <object id="1" x="7" y="1">
    <polyline points="0,0 -4,10.9688 -4.03125,14.9375 5.96875,15.0313 5.96875,11.0625 1.9375,0.0625 0.03125,0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="589">
  <objectgroup draworder="index">
   <object id="2" x="0.0434783" y="5.91304">
    <polyline points="0,0 15.9565,0.0434783 16,10.1304 0,10.1739 -0.0434783,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="590">
  <objectgroup draworder="index">
   <object id="1" x="0.0434783" y="6.04348">
    <polyline points="0,0 15.9565,0.0434783 16,10.1304 0,10.1739 -0.0434783,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="591">
  <objectgroup draworder="index">
   <object id="1" x="0.0434783" y="6.04348">
    <polyline points="0,0 15.9565,0.0434783 16,10.1304 0,10.1739 -0.0434783,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="593">
  <objectgroup draworder="index">
   <object id="1" x="7.08696" y="1.04348">
    <polyline points="-1.30435,0 -6.30435,7.01228 -7.20516,14.8505 8.83832,14.8139 7.75136,6.93207 2.67663,0.105978 -1.22962,0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="594">
  <objectgroup draworder="index">
   <object id="4" x="0.0078125" y="13.9922">
    <polyline points="0,0 -0.0261285,-5.99609 3.97387,-9.99609 11.9294,-9.99609 15.9091,-6.03957 15.9961,-0.126529 12.8657,2.04738 2.99609,2.04738 0.039572,0.0473845"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="597">
  <objectgroup draworder="index">
   <object id="1" x="4" y="16">
    <polyline points="0,0 -2.43478,-9.3913 0.869565,-16.087 5.86957,-16.0435 9.95652,-8.86957 7,-0.0434783 0,-0.0869565"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="611">
  <objectgroup draworder="index">
   <object id="1" x="1.98889" y="16">
    <polyline points="0,0 0.0111111,-11 3.97986,-15.0625 8.91736,-15.0625 12.9174,-11.0625 12.9486,-0.09375 0.0736111,-0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="616">
  <objectgroup draworder="index">
   <object id="2" x="15" y="0.03125">
    <polyline points="0,0 -14,13.9063 -14.0313,15.875 1,15.9688 1.03125,0 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="617">
  <objectgroup draworder="index">
   <object id="1" x="2" y="0.0625">
    <polyline points="0,0 13.9063,13.9375 13.9063,15.875 -2,15.9375 -1.96875,-0.15625 -0.125,-0.0625"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="618">
  <objectgroup draworder="index">
   <object id="1" x="15.087" y="0.0869565">
    <polyline points="0,0 -14,13.9063 -14.0313,15.875 1,15.9688 1.03125,0 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="619">
  <objectgroup draworder="index">
   <object id="1" x="1.95652" y="0.0869565">
    <polyline points="0,0 13.9063,13.9375 13.9063,15.875 -2,15.9375 -1.96875,-0.15625 -0.125,-0.0625"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="620">
  <objectgroup draworder="index">
   <object id="1" x="1.86957" y="15.913">
    <polyline points="0,0 1.17391,-7.21739 11.1304,-7.13043 13.1304,-0.0434783 0.173913,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="621">
  <objectgroup draworder="index">
   <object id="2" x="7" y="16">
    <polyline points="0,0 -3.71875,-6.65625 0.96875,-11.9063 5.9375,-6.59375 2.03125,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="622">
  <objectgroup draworder="index">
   <object id="1" x="7.03125" y="16.0625">
    <polyline points="0,0 -4.1875,-6.96875 0.90625,-13.2813 6.125,-7.1875 2.03125,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="623">
  <objectgroup draworder="index">
   <object id="1" x="7.03125" y="16.0313">
    <polyline points="0,0 -3.71875,-6.65625 0.96875,-11.9063 5.9375,-6.59375 2.03125,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="640">
  <objectgroup draworder="index">
   <object id="1" x="5.01563" y="16.0156">
    <polyline points="0,0 -2.11821,-6.08696 -4.97554,-10.1114 -5.04348,-16.0367 10.8261,-16.087 10.9253,-10.1861 7.92532,-6.0625 5.96875,-0.03125 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="641">
  <objectgroup draworder="index">
   <object id="1" x="5" y="16.0156">
    <polyline points="0,0 -2.16168,-6.08696 -4.84511,-10.1549 -4.91304,-15.9933 11.0435,-16 11.0558,-10.0992 8.01228,-6.01902 5.96875,-0.03125 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="642">
  <objectgroup draworder="index">
   <object id="1" x="5.03125" y="16.0625">
    <polyline points="0,0 -2.11821,-6.13043 -4.97554,-10.2418 -5.08696,-16.0802 10.9565,-16.087 10.8818,-10.3601 7.88184,-5.97554 5.96875,-0.03125 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="643">
  <objectgroup draworder="index">
   <object id="1" x="5.03125" y="16.0313">
    <polyline points="0,0 -5.03125,-5 -5.0625,-7.9375 -3,-15.9063 9,-16 10.9688,-7.96875 10.9688,-5.0625 5.96875,-0.03125 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="644">
  <objectgroup draworder="index">
   <object id="4" x="5.0625" y="16">
    <polyline points="0,0 -5.03125,-5 -5.0625,-7.9375 -3,-15.9063 9,-16 10.9688,-7.96875 10.9688,-5.0625 5.96875,-0.03125 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="645">
  <objectgroup draworder="index">
   <object id="1" x="5" y="15.9688">
    <polyline points="0,0 -5.03125,-5 -5.0625,-7.9375 -3,-15.9063 9,-16 10.9688,-7.96875 10.9688,-5.0625 5.96875,-0.03125 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="646">
  <objectgroup draworder="index">
   <object id="1" x="0" y="5.86957">
    <polyline points="0,0 15.9565,0.0434783 16,10.1304 0,10.1739 -0.0434783,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="647">
  <objectgroup draworder="index">
   <object id="1" x="0.0434783" y="6.04348">
    <polyline points="0,0 15.9565,0.0434783 16,10.1304 0,10.1739 -0.0434783,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="648">
  <objectgroup draworder="index">
   <object id="3" x="0.0434783" y="5.95652">
    <polyline points="0,0 15.9565,0.0434783 16,10.1304 0,10.1739 -0.0434783,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="650">
  <objectgroup draworder="index">
   <object id="1" x="5.04348" y="16">
    <polyline points="0,0 -1.94429,-6 -5.0625,-9.98098 -5.08696,-15.9498 10.8696,-16 10.8818,-10.0557 7.92532,-6.0625 5.96875,-0.03125 0.0625,0"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="651">
  <objectgroup draworder="index">
   <object id="1" x="0.00390625" y="13.9961">
    <polyline points="0,0 -0.0261285,-5.99609 3.97387,-9.99609 11.9294,-9.99609 15.9091,-6.03957 15.9961,-0.126529 12.8657,2.04738 2.99609,2.04738 0.039572,0.0473845"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="654">
  <objectgroup draworder="index">
   <object id="1" x="5.03125" y="15.9688">
    <polyline points="0,0 -4.0625,-10.9688 -1,-15.0313 8.90625,-14.9688 9.9375,-9.0625 6.96875,0 0.03125,-0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="673">
  <objectgroup draworder="index">
   <object id="2" x="0.96875" y="-0.03125">
    <polyline points="0,0 0.0625,14.9063 1.03125,15.9688 11.9844,16.0156 12.0469,14.0156 14.9688,14.0625 15.0313,0.015625 0.03125,-0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="674">
  <objectgroup draworder="index">
   <object id="2" x="1.3125" y="1.40625">
    <polyline points="-1.28125,-1.40625 -1.25,12.5938 2.6875,12.6251 2.6563,14.5626 13.7188,14.5625 14.6563,13.5313 14.6406,-1.45313 -1.3125,-1.46875"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="675">
  <objectgroup draworder="index">
   <object id="1" x="1" y="0.0434783">
    <polyline points="0,0 0.0625,14.9063 1.03125,15.9688 11.9844,16.0156 12.0469,14.0156 14.9688,14.0625 15.0313,0.015625 0.03125,-0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="676">
  <objectgroup draworder="index">
   <object id="2" x="1.36345" y="1.3913">
    <polyline points="-1.28125,-1.40625 -1.25,12.5938 2.6875,12.6251 2.6563,14.5626 13.7188,14.5625 14.6563,13.5313 14.6406,-1.45313 -1.3125,-1.46875"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="677">
  <objectgroup draworder="index">
   <object id="1" x="1" y="15.9565">
    <polyline points="0,0 1.04348,-15.913 14.8696,-15.913 15.0435,-0.0869565 0.0434783,-0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1136">
  <objectgroup draworder="index">
   <object id="1" x="2.04348" y="13">
    <polyline points="0,0 2,-7.86957 3.04348,-9.08692 9.86952,-9.04348 12.9565,-0.260873 6.13043,0.913047 -0.0869565,0.0869565"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1193">
  <objectgroup draworder="index">
   <object id="1" x="2" y="12.087">
    <polyline points="0,0 3.13044,-8.95653 9.91304,-8.99997 13.1304,-4.13043 10.913,1.91304 2,1.82609 -0.0869565,0.0869565"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1250">
  <objectgroup draworder="index">
   <object id="1" x="2" y="12.0435">
    <polyline points="0,0 3.08696,-8.08696 5.95652,-12.1304 13.913,-6 10.913,1.91304 2,1.82609 -0.0869565,0.0869565"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1307">
  <objectgroup draworder="index">
   <object id="1" x="3.04348" y="15.913">
    <polyline points="0,0 -1.08696,-1 -1.17391,-10.9565 1.78261,-14.913 8.91304,-15 11.8261,-10.9565 11.9565,-1.08696 10.9565,0.130435 0.0434783,0.0434783"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1365">
  <objectgroup draworder="index">
   <object id="12" x="7.78125" y="3.59375">
    <polyline points="0,0 -5.75,7.40625 0.4375,9.3125 7.1875,7.65625 0.25,-0.03125"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1366">
  <objectgroup draworder="index">
   <object id="1" x="3.375" y="10.7813">
    <polyline points="0,0 3.3125,-6.46875 10.5625,0.96875 2.71875,2.15625 0.03125,0.0625"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="1367">
  <objectgroup draworder="index">
   <object id="1" x="2.96875" y="11.125">
    <polyline points="0,0 7.4375,-6.625 11.0938,0.9375 5.71875,1.9375 -0.03125,0.09375"/>
   </object>
  </objectgroup>
 </tile>
</tileset>
