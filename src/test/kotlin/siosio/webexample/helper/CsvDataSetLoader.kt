package siosio.webexample.helper

import com.github.springtestdbunit.dataset.*
import org.dbunit.dataset.*
import org.dbunit.dataset.csv.*
import org.springframework.core.io.*


class CsvDataSetLoader : AbstractDataSetLoader() {

    override fun createDataSet(resource: Resource): IDataSet {
        return CsvURLDataSet(resource.url)
    }
}
