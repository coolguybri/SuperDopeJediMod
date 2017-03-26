package superdopesquad.superdopejedimod.teleporter;

import net.minecraft.util.math.BlockPos;
//import superdopesquad.superdopejedimod.faction.ClassCapabilityInterface;


public class TeleporterCapability implements TeleporterCapabilityInterface {


	private BlockPos _blockPos = new BlockPos(0,0,0); // don't let this be null, pain and suffering will follow.


	@Override
	public boolean setTeleporterDestination(BlockPos blockPos) {

		this._blockPos = blockPos;
		
		return true;
	}


	@Override
	public BlockPos getTeleporterDestination() {
		
		return this._blockPos;
	}
}