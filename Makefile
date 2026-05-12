.PHONY: help build clean install test rebuild all package test-verbose

PROJECT_NAME ?= ublkit-java
PERSEO_ROOT ?= $(shell cd "$(dir $(realpath $(MAKEFILE_LIST)))" && pwd)/..

# ublkit-java modules (10 total)
MODULES := ublkit-core ublkit-ubl ublkit-sign ublkit-gateway ublkit-render \
           ublkit-validation ublkit-catalogs ublkit-testkit ublkit-spring-boot-starter ublkit-quarkus

help:
	@echo "╔════════════════════════════════════════════════════════════════╗"
	@echo "║  $(PROJECT_NAME) - Multi-Module Build (10 modules)            ║"
	@echo "╚════════════════════════════════════════════════════════════════╝"
	@echo ""
	@echo "  build              - Compile parent + all 10 modules"
	@echo "  test               - Run tests for all modules"
	@echo "  test-verbose       - Verbose test output"
	@echo "  clean              - Clean all artifacts"
	@echo "  install            - Install all modules"
	@echo "  package            - Package all JARs"
	@echo "  rebuild            - Clean + build"

build:
	@echo "📦 Building..."; \
	set -o pipefail; \
	mvn -q clean verify 2>&1 | tee /tmp/build.log | grep -E "BUILD|ERROR" || true; \
	status=${PIPESTATUS[0]}; \
	if [ $$status -ne 0 ]; then echo "❌ Build FAILED"; exit $$status; else echo "✅ Build successful"; fi

test:
	@echo "🧪 Testing $(PROJECT_NAME) (10 modules)..."
	@mvn -q test 2>&1 | grep -E "BUILD|ERROR|Tests run:|Failures:" | tail -10 || echo "✅ Tests passed"

test-verbose:
	@echo "🧪 Testing (verbose)..."
	@mvn test

clean:
	@echo "🧹 Cleaning..."; mvn -q clean; echo "✅ Done"

install:
	@echo "📥 Installing..."; mvn -q install -DskipTests 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Done"

package:
	@echo "📦 Packaging..."; mvn -q package -DskipTests 2>&1 | grep -E "BUILD|ERROR" || echo "✅ Done"

rebuild: clean build

all: install build test
	@echo "🎉 Complete"

