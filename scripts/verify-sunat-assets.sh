#!/usr/bin/env bash
set -euo pipefail

repo_root="$(cd "$(dirname "$0")/.." && pwd)"
asset_root="$repo_root/ublkit-validation/src/main/resources/sunat"
lock_file="$asset_root/sunat-assets.lock.tsv"
mode="${1:---check}"
tmp_file="$(mktemp)"
trap 'rm -f "$tmp_file"' EXIT

{
  echo '# package-version: sunat-assets-2026-10-19'
  echo '# verified-at: 2026-10-19'
  echo '# normative-source: https://cpe.sunat.gob.pe/guias-y-manuales'
  echo '# format: sha256<TAB>bytes<TAB>relative-path'
  find "$asset_root" -type f \( -name '*.xsd' -o -name '*.xsl' -o -path '*/catalogo/*.xml' \) \
    ! -name 'sunat-assets.lock.tsv' -print0 \
    | sort -z \
    | while IFS= read -r -d '' file; do
        hash="$(shasum -a 256 "$file" | awk '{print $1}')"
        bytes="$(wc -c < "$file" | tr -d ' ')"
        relative="${file#"$asset_root/"}"
        printf '%s\t%s\t%s\n' "$hash" "$bytes" "$relative"
      done
} > "$tmp_file"

if [[ "$mode" == "--apply" ]]; then
  mv "$tmp_file" "$lock_file"
  trap - EXIT
  echo "SUNAT asset lock actualizado: $lock_file"
  exit 0
fi

if [[ "$mode" != "--check" ]]; then
  echo "Uso: $0 [--check|--apply]" >&2
  exit 2
fi

if [[ ! -f "$lock_file" ]] || ! cmp -s "$tmp_file" "$lock_file"; then
  echo 'Los XSD/XSL/catálogos SUNAT difieren del lock versionado.' >&2
  echo 'Revise la publicación oficial y ejecute scripts/verify-sunat-assets.sh --apply.' >&2
  exit 1
fi

echo 'SUNAT assets verificados.'
