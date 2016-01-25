package com.sbpinvertor.modbus.data.base;

import com.sbpinvertor.modbus.Modbus;
import com.sbpinvertor.modbus.data.ModbusOutputStream;
import com.sbpinvertor.modbus.exception.ModbusNumberException;

import java.io.IOException;

/**
 * Copyright (c) 2015-2016 JSC "Zavod "Invertor"
 * [http://www.sbp-invertor.ru]
 * <p/>
 * This file is part of JLibModbus.
 * <p/>
 * JLibModbus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * Authors: Vladislav Y. Kochedykov, software engineer.
 * email: vladislav.kochedykov@gmail.com
 */
abstract public class AbstractDataRequest extends ModbusRequest {

    private int startAddress;

    protected AbstractDataRequest(int serverAddress) throws ModbusNumberException {
        super(serverAddress);
    }

    protected AbstractDataRequest(int serverAddress, int startAddress) throws ModbusNumberException {
        super(serverAddress);

        if (!Modbus.checkStartAddress(startAddress))
            throw new ModbusNumberException("Error in start address", startAddress);

        setStartAddress(startAddress);
    }

    public AbstractDataRequest(AbstractDataRequest msg) {
        super(msg);

        setStartAddress(msg.startAddress);
    }

    abstract protected void writeData(ModbusOutputStream fifo) throws IOException;

    @Override
    protected void writeRequest(ModbusOutputStream fifo) throws IOException {
        fifo.writeShortBE(getStartAddress());
        writeData(fifo);
    }

    private int getStartAddress() {
        return startAddress;
    }

    private void setStartAddress(int startAddress) {
        this.startAddress = startAddress;
    }

    @Override
    final protected int requestSize() {
        return 2 + dataSize();
    }

    abstract protected int dataSize();
}
