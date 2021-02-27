package ru.storage;

import io.art.tarantool.constants.*;
import io.art.tarantool.instance.*;
import org.apache.logging.log4j.*;
import ru.model.*;

import java.util.*;

import static io.art.logging.LoggingModule.*;
import static io.art.storage.module.StorageModule.*;
import static io.art.tarantool.configuration.space.TarantoolSpaceConfig.*;
import static io.art.tarantool.configuration.space.TarantoolSpaceFormat.*;
import static io.art.tarantool.configuration.space.TarantoolSpaceIndex.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.TarantoolFieldType.*;
import static io.art.tarantool.constants.TarantoolModuleConstants.TarantoolIndexIterator.*;
import static io.art.tarantool.model.operation.TarantoolFilterOperation.*;
import static io.art.tarantool.module.TarantoolModule.*;
import static ru.storage.ExampleStorageInterfaces.StorageExampleModelFieldNames.*;
import static ru.storage.ExampleStorageInterfaces.*;

public class TarantoolExample {
    private static final Random random = new Random();
    private static final int recordsCount = 1000;
    private static final String clusterName = "storage1";
    private static final String spaceName = "example";
    private static final Logger logger = logger(TarantoolExample.class);

    public static void storageExample(){
        initializeSpace();
        StorageExampleModelSpace space = space(StorageExampleModel.class);

        for (int i = 0; i<recordsCount; i++){
            space.insert(newRecord()).synchronize();
        }
        logger.info("Inserted " + space.count().get() + " records to space");

        space.select(500)
                .iterator(LE)
                .filter(data.less(700))
                .filter(more(data, 600))
                .sortBy(data.ascending())
                .limit(10)
                .stream()
                .forEach(item -> logger.info("Got item id: " + item.id + ", data: " + item.data));

        dropSpace();
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
                .ifNotExists(true)
                .sequence());
        db.createIndex(spaceName, "data", tarantoolSpaceIndex()
                .part(3)
                .unique(false)
                .ifNotExists(true));
        logger.info("Space initialized");
    }

    private static void dropSpace(){
        TarantoolInstance db = tarantoolInstance(clusterName);
        db.dropSpace(spaceName);
        logger.info("Dropped space");
    }

    private static StorageExampleModel newRecord(){
        StorageExampleModel model = new StorageExampleModel();
        model.setData(random.nextInt(recordsCount));
        return model;
    }
}
