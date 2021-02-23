package ru.storage;

import io.art.core.collection.*;
import io.art.tarantool.constants.*;
import io.art.tarantool.instance.*;
import org.apache.logging.log4j.*;
import ru.*;
import ru.model.*;

import java.util.*;

import static io.art.core.extensions.ThreadExtensions.*;
import static io.art.logging.LoggingModule.*;
import static io.art.storage.module.StorageModule.*;
import static io.art.tarantool.configuration.space.TarantoolSpaceConfig.*;
import static io.art.tarantool.configuration.space.TarantoolSpaceFormat.*;
import static io.art.tarantool.configuration.space.TarantoolSpaceIndex.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.TarantoolFieldType.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.TarantoolIndexIterator.*;
import static io.art.tarantool.model.operation.TarantoolFilterOperation.*;
import static io.art.tarantool.module.TarantoolModule.*;

public class TarantoolExample {
    private static final Random random = new Random();
    private static final int recordsCount = 1000;
    private static final String clusterName = "storage1";
    private static final String spaceName = "example";
    private static final Logger logger = logger(TarantoolExample.class);

    public static void storageExample(){
        //initializeSpace();
        ExampleStorageInterfaces.StorageExampleModelSpace space = space(StorageExampleModel.class);

//        for (int i = 0; i<recordsCount; i++){
//            space.insert(newRecord((long)i)).synchronize();
//        }
        sleep(1000);
        logger.info("Added " + space.count().get() + " records to space");

        StorageExampleModel item1 = space.get(1).get();
        logger.info("Got item1 id: " + item1.id + ", data: " + item1.data);

        ImmutableArray<StorageExampleModel> results = space.select(500)
                .iterator(LE)
                .filter(less(2L, 700))
                .filter(more(2L, 600))
                .limit(10L)
                .get();

        sleep(1000);

        results.forEach(item -> logger.info("Got item id: " + item.id + ", data: " + item.data));

//        dropSpace();
    }

    private static void initializeSpace(){
        TarantoolInstance db = tarantoolInstance(clusterName);
        db.createSpace(spaceName, tarantoolSpaceConfig()
                .ifNotExists(true));
        db.formatSpace(spaceName, tarantoolSpaceFormat()
                .field("id", UNSIGNED, false));
        db.createIndex(spaceName, "primary", tarantoolSpaceIndex()
                .type(TarantoolModuleConstants.TarantoolIndexType.TREE)
                .part("id")
                .ifNotExists(true)
                .unique(true)
                .sequence());
        db.createIndex(spaceName, "data", tarantoolSpaceIndex()
                .part(3)
                .unique(false));
        logger.info("Created space");
    }

    private static void dropSpace(){
        TarantoolInstance db = tarantoolInstance(clusterName);
        db.dropSpace(spaceName);
        logger.info("Dropped space");
    }

    private static StorageExampleModel newRecord(Long id){
        return new StorageExampleModel(null, random.nextInt(recordsCount));
    }
}
