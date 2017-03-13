package bitcoin.queries;

import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.utils.BlockFileLoader;
import org.bitcoinj.utils.BriefLogFormatter;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Initalize bitcoinJ
        BriefLogFormatter.init();
        NetworkParameters networkParameters = new MainNetParams();
        Context.getOrCreate(MainNetParams.get());

        // Read the blockchain files from the disk
        List<File> blockChainFiles = new LinkedList<File>();
        for (int i = 0; true; i++) {
            File file = new File(Settings.BLOCKCHAIN_PATH + String.format(Locale.US, "blk%05d.dat", i));
            if (!file.exists())
                break;
            blockChainFiles.add(file);
        }

        long startTime = System.currentTimeMillis();

        BlockFileLoader bfl = new BlockFileLoader(networkParameters, blockChainFiles);

        // Iterate over the blocks in the blockchain.
        int height = 1;
        for (Block block : bfl) {
            height++;

            if(height % 1000 == 0){
                System.out.println("Current block: " + height);
            }

            for (Transaction t : block.getTransactions()){
            	System.out.println(t);
            }
        }

        System.out.println("Elapsed time: " + (System.currentTimeMillis()-startTime)/1000);
    }
}
