/*
 *  Wave, containers provisioning service
 *  Copyright (c) 2023-2024, Seqera Labs
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.seqera.wave.service.cleanup

import spock.lang.Specification

/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
class CleanupServiceTest extends Specification {

    /**
     * Groovy 5 cannot coerce a stubbed response to {@code void}, therefore a Spy can no longer
     * suppress a real void method with {@code >> null}; the no-op overrides below replace that
     */
    static class TestCleanupService extends CleanupServiceImpl {
        @Override protected void cleanupJob0(String jobName) { }
        @Override protected void cleanupDir0(String path) { }
        @Override protected void cleanupScanId0(String scanId) { }
    }

    def 'should validate cleanup entry' () {
        given:
        def service = Spy(new TestCleanupService())

        when:
        service.cleanupEntry('job:foo')
        then:
        1 * service.cleanupJob0('foo')

        when:
        service.cleanupEntry('dir:/some/data/dir')
        then:
        1 * service.cleanupDir0('/some/data/dir')

        when:
        service.cleanupEntry('scanid:foo:bar')
        then:
        1 * service.cleanupScanId0('foo:bar')
    }
}
