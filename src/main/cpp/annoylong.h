#include "annoylib.h"
#include "kissrandom.h"

extern "C" {
long createAngular(int f) {
  return (long)new AnnoyIndex<long, float, Angular, Kiss64Random>(f);
}

long createDotProduct(int f) {
  return (long)new AnnoyIndex<long, float, DotProduct, Kiss64Random>(f);
}

long createEuclidean(int f) {
  return (long)new AnnoyIndex<long, float, Euclidean, Kiss64Random>(f);
}

long createManhattan(int f) {
  return (long)new AnnoyIndex<long, float, Manhattan, Kiss64Random>(f);
}

long createHamming(int f) {
  return (long)new HammingWrapper<Kiss64Random>(f);
}

void deleteIndex(long ptr) {
  delete (AnnoyIndexInterface<int64_t, float> *)ptr;
}

void addItem(long ptr, int64_t item, float *w) {
  ((AnnoyIndexInterface<int64_t, float> *)ptr)->add_item(item, w);
}

void build(long ptr, int q) {
  ((AnnoyIndexInterface<int64_t, float> *)ptr)->build(q);
}

bool save(long ptr, char *filename) {
  return ((AnnoyIndexInterface<int64_t, float> *)ptr)->save(filename);
}

void unload(long ptr) {
  ((AnnoyIndexInterface<int64_t, float> *)ptr)->unload();
}

bool load(long ptr, char *filename) {
  return ((AnnoyIndexInterface<int64_t, float> *)ptr)->load(filename);
}

float getDistance(long ptr, int64_t i, int64_t j) {
  return ((AnnoyIndexInterface<int64_t, float> *)ptr)->get_distance(i, j);
}

void getNnsByItem(long ptr, int64_t item, int n,
                  int search_k, int64_t *result, float *distances) {
  vector<int64_t> resultV;
  vector<float> distancesV;
  ((AnnoyIndexInterface<int64_t, float> *)ptr)->get_nns_by_item(item, n, search_k, &resultV, &distancesV);
  std::copy(resultV.begin(), resultV.end(), result);
  std::copy(distancesV.begin(), distancesV.end(), distances);
}

void getNnsByVector(long ptr, float *w, int n,
                    int search_k, int64_t *result, float *distances) {
  vector<int64_t> resultV;
  vector<float> distancesV;
  ((AnnoyIndexInterface<int64_t, float> *)ptr)->get_nns_by_vector(w, n, search_k, &resultV, &distancesV);
  std::copy(resultV.begin(), resultV.end(), result);
  std::copy(distancesV.begin(), distancesV.end(), distances);
}

int getNItems(long ptr) {
  return (int)((AnnoyIndexInterface<int64_t, float> *)ptr)->get_n_items();
}

void verbose(long ptr, bool v) {
  ((AnnoyIndexInterface<int64_t, float> *)ptr)->verbose(v);
}

void getItem(long ptr, int64_t item, float *v) {
  ((AnnoyIndexInterface<int64_t, float> *)ptr)->get_item(item, v);
}
}