/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.pagememory.persistence.io;

import static org.apache.ignite.internal.pagememory.PageIdAllocator.FLAG_AUX;
import static org.apache.ignite.internal.pagememory.util.PageUtils.getInt;
import static org.apache.ignite.internal.pagememory.util.PageUtils.getLong;
import static org.apache.ignite.internal.pagememory.util.PageUtils.putInt;
import static org.apache.ignite.internal.pagememory.util.PageUtils.putLong;

import org.apache.ignite.internal.pagememory.io.IoVersions;
import org.apache.ignite.internal.pagememory.io.PageIo;
import org.apache.ignite.lang.IgniteStringBuilder;

/**
 * Io for partition metadata pages.
 */
public class PartitionMetaIo extends PageIo {
    private static final int TREE_ROOT_PAGE_ID_OFF = COMMON_HEADER_END;

    private static final int REUSE_LIST_ROOT_PAGE_ID_OFF = TREE_ROOT_PAGE_ID_OFF + Long.BYTES;

    private static final int VERSION_CHAIN_TREE_ROOT_PAGE_ID_OFF = REUSE_LIST_ROOT_PAGE_ID_OFF + Long.BYTES;

    private static final int VERSION_CHAIN_FREE_LIST_ROOT_PAGE_ID_OFF = VERSION_CHAIN_TREE_ROOT_PAGE_ID_OFF + Long.BYTES;

    private static final int ROW_VERSION_FREE_LIST_ROOT_PAGE_ID_OFF = VERSION_CHAIN_FREE_LIST_ROOT_PAGE_ID_OFF + Long.BYTES;

    private static final int PAGE_COUNT_OFF = ROW_VERSION_FREE_LIST_ROOT_PAGE_ID_OFF + Long.BYTES;

    /** Page IO type. */
    public static final short T_TABLE_PARTITION_META_IO = 7;

    /** I/O versions. */
    public static final IoVersions<PartitionMetaIo> VERSIONS = new IoVersions<>(new PartitionMetaIo(1));

    /**
     * Constructor.
     *
     * @param ver Page format version.
     */
    protected PartitionMetaIo(int ver) {
        super(T_TABLE_PARTITION_META_IO, ver, FLAG_AUX);
    }

    /** {@inheritDoc} */
    @Override
    public void initNewPage(long pageAddr, long pageId, int pageSize) {
        super.initNewPage(pageAddr, pageId, pageSize);

        setTreeRootPageId(pageAddr, 0);
        setReuseListRootPageId(pageAddr, 0);
        setVersionChainTreeRootPageId(pageAddr, 0);
        setVersionChainFreeListRootPageId(pageAddr, 0);
        setRowVersionFreeListRootPageId(pageAddr, 0);
        setPageCount(pageAddr, 0);
    }

    /**
     * Sets tree root page ID.
     *
     * @param pageAddr Page address.
     * @param pageId Tree root page ID.
     */
    // TODO: IGNITE-17466 Delete it
    public void setTreeRootPageId(long pageAddr, long pageId) {
        assertPageType(pageAddr);

        putLong(pageAddr, TREE_ROOT_PAGE_ID_OFF, pageId);
    }

    /**
     * Returns tree root page ID.
     *
     * @param pageAddr Page address.
     */
    // TODO: IGNITE-17466 Delete it
    public long getTreeRootPageId(long pageAddr) {
        return getLong(pageAddr, TREE_ROOT_PAGE_ID_OFF);
    }

    /**
     * Sets reuse list root page ID.
     *
     * @param pageAddr Page address.
     * @param pageId Reuse list root page ID.
     */
    // TODO: IGNITE-17466 Delete it
    public void setReuseListRootPageId(long pageAddr, long pageId) {
        assertPageType(pageAddr);

        putLong(pageAddr, REUSE_LIST_ROOT_PAGE_ID_OFF, pageId);
    }

    /**
     * Returns reuse list root page ID.
     *
     * @param pageAddr Page address.
     */
    // TODO: IGNITE-17466 Delete it
    public long getReuseListRootPageId(long pageAddr) {
        return getLong(pageAddr, REUSE_LIST_ROOT_PAGE_ID_OFF);
    }

    /**
     * Sets version chain tree root page ID.
     *
     * @param pageAddr Page address.
     * @param pageId Version chain tree root page ID.
     */
    public void setVersionChainTreeRootPageId(long pageAddr, long pageId) {
        assertPageType(pageAddr);

        putLong(pageAddr, VERSION_CHAIN_TREE_ROOT_PAGE_ID_OFF, pageId);
    }

    /**
     * Returns version chain tree root page ID.
     *
     * @param pageAddr Page address.
     */
    public long getVersionChainTreeRootPageId(long pageAddr) {
        return getLong(pageAddr, VERSION_CHAIN_TREE_ROOT_PAGE_ID_OFF);
    }

    /**
     * Sets version chain free list root page ID.
     *
     * @param pageAddr Page address.
     * @param pageId Version chain free list root page ID.
     */
    public void setVersionChainFreeListRootPageId(long pageAddr, long pageId) {
        assertPageType(pageAddr);

        putLong(pageAddr, VERSION_CHAIN_FREE_LIST_ROOT_PAGE_ID_OFF, pageId);
    }

    /**
     * Returns version chain free list root page ID.
     *
     * @param pageAddr Page address.
     */
    public long getVersionChainFreeListRootPageId(long pageAddr) {
        return getLong(pageAddr, VERSION_CHAIN_FREE_LIST_ROOT_PAGE_ID_OFF);
    }

    /**
     * Sets row version free list root page ID.
     *
     * @param pageAddr Page address.
     * @param pageId Row version free list root page ID.
     */
    public void setRowVersionFreeListRootPageId(long pageAddr, long pageId) {
        assertPageType(pageAddr);

        putLong(pageAddr, ROW_VERSION_FREE_LIST_ROOT_PAGE_ID_OFF, pageId);
    }

    /**
     * Returns row version free list root page ID.
     *
     * @param pageAddr Page address.
     */
    public long getRowVersionFreeListRootPageId(long pageAddr) {
        return getLong(pageAddr, ROW_VERSION_FREE_LIST_ROOT_PAGE_ID_OFF);
    }

    /**
     * Sets the count of pages.
     *
     * @param pageAddr Page address.
     * @param pageCount Count of pages.
     */
    public void setPageCount(long pageAddr, int pageCount) {
        assertPageType(pageAddr);

        putInt(pageAddr, PAGE_COUNT_OFF, pageCount);
    }

    /**
     * Returns the page count.
     *
     * @param pageAddr Page address.
     */
    public int getPageCount(long pageAddr) {
        return getInt(pageAddr, PAGE_COUNT_OFF);
    }

    /** {@inheritDoc} */
    @Override
    protected void printPage(long addr, int pageSize, IgniteStringBuilder sb) {
        sb.app("TablePartitionMeta [").nl()
                .app("treeRootPageId=").appendHex(getTreeRootPageId(addr)).nl()
                .app(", reuseListRootPageId=").appendHex(getReuseListRootPageId(addr)).nl()
                .app(", versionChainTreeRootPageId=").appendHex(getVersionChainTreeRootPageId(addr)).nl()
                .app(", versionChainFreeListRootPageId=").appendHex(getVersionChainFreeListRootPageId(addr)).nl()
                .app(", rowVersionFreeListRootPageId=").appendHex(getRowVersionFreeListRootPageId(addr)).nl()
                .app(", pageCount=").app(getPageCount(addr)).nl()
                .app(']');
    }
}
